package Config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachingConfigurationSelector;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.File;
import java.util.HashMap;
@Configuration
@EnableCaching //开启注解
public class RedisConfig extends CachingConfigurationSelector {
	protected final static Logger logger = LoggerFactory.getLogger(RedisConfig.class);
	protected static Gson gson = new Gson();
	static HashMap<String, String> data;
	static String conf;
	static long lastModified = 0;

	@Bean
	public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory) {

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		// 配置连接工厂
		template.setConnectionFactory(factory);
		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

		ObjectMapper om = new ObjectMapper();
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jacksonSeial.setObjectMapper(om);

		// 值采用json序列化
		template.setValueSerializer(jacksonSeial);
		//使用StringRedisSerializer来序列化和反序列化redis的key值
		template.setKeySerializer(new StringRedisSerializer());

		// 设置hash key 和value序列化模式
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(jacksonSeial);
		template.afterPropertiesSet();

		return template;


	}
	/**
	 * 对hash类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForHash();
	}
	/**
	 * 对redis字符串类型数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ValueOperations<String, Object> valueOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	/**
	 * 对链表类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ListOperations<String, Object> listOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForList();
	}

	/**
	 * 对无序集合类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	/**
	 * 对有序集合类型的数据操作
	 *
	 * @param redisTemplate
	 * @return
	 */
	@Bean
	public ZSetOperations<String, Object> zSetOperations(RedisTemplate<String, Object> redisTemplate) {
		return redisTemplate.opsForZSet();
	}

	public static String get(String key) {
		if (data.containsKey(key)) {
			return data.get(key);
		}
		return null;
	}

	public static int getInt(String key) {
		String val = get(key);
		if (val != null) {
			return Integer.parseInt(val);
		}
		return 0;
	}
	public static void load(String conf, boolean check) throws Exception {
		RedisConfig.conf = conf;
		reload();
		if (check) {
			new BsConfig.ReloadThread().start();
		}
	}
	public static void load(String conf) throws Exception {
		load(conf,true);
	}
	public static void reload() throws Exception {
		File file = new File(conf);
		if (!file.exists()) {
			logger.warn("file is not exists : " + conf);
			return;
		}
		if (file.lastModified() <= lastModified) {
			// logger.debug("file is not modified : " + conf);
			return;
		}
		String json = FileHelper.readFileContent(conf);
		logger.info("reload config begin :" + conf);
		logger.info("config:" + json);
		data = gson.fromJson(json, new TypeToken<HashMap<String, String>>() {
		}.getType());
		logger.info("reload config end:" + conf);
		lastModified = file.lastModified();

	}

	static class ReloadThread extends Thread {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1000);
					reload();
				} catch (Exception e) {

				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		RedisConfig.load("conf/redis.conf");
	}
}

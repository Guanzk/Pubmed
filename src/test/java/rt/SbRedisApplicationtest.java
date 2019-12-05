package rt;
import	java.lang.annotation.Target;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SbRedisApplicationtest {

    @Autowired
    RedisTemplate<String,String> redisTemplate;


    @Test
    public void testString(){
        System.out.println(redisTemplate.toString());

        LettuceConnectionFactory jedisConnectionFactory =( LettuceConnectionFactory)redisTemplate.getConnectionFactory();
        //ValueOperations<String, String> valuestr =redisTemplate.opsForValue();
        jedisConnectionFactory.setDatabase(3);
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        jedisConnectionFactory.resetConnection();
        ValueOperations<String, String> valuestr =redisTemplate.opsForValue();
        String name= valuestr.get("8235381");
        System.out.println("name: "+name);


    }

}

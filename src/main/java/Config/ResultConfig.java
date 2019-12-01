package Config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;

public class ResultConfig {
	protected final static Logger logger = LoggerFactory.getLogger(ResultConfig.class);
	protected static Gson gson = new Gson();
	static HashMap<String, String> data;
	static String conf;
	static long lastModified = 0;

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
		ResultConfig.conf = conf;
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
		RedisConfig.load("conf/result.conf");
	}
}

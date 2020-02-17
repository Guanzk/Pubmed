//package com.searchproject.pubmed.config;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.util.HashMap;
//
//public class BsConfig {
//
//    protected final static Logger logger = LoggerFactory.getLogger(BsConfig.class);
//
//    public final static String CF_THREADNUM = "threadNumber";
//    public final static String CF_PORT = "port";
//
//    protected static Gson gson = new Gson();
//    static HashMap<String, String> data;
//    static String conf;
//    static long lastModified = 0;
//
//    public static String get(String key) {
//        if (data.containsKey(key)) {
//            return data.get(key);
//        }
//        return null;
//    }
//
//    public static int getInt(String key) {
//        String val = get(key);
//        if (val != null) {
//            return Integer.parseInt(val);
//        }
//        return 0;
//    }
//
//    public static boolean getBool(String key) {
//        String val = get(key);
//        if (val != null) {
//            return Boolean.parseBoolean(val);
//        }
//        return false;
//    }
//
//    public static long getLong(String key) {
//        String val = get(key);
//        if (val != null) {
//            return Long.parseLong(val);
//        }
//        return 0;
//    }
//
//    public static float getFloat(String key) {
//        String val = get(key);
//        if (val != null) {
//            return Float.parseFloat(val);
//        }
//        return 0.0f;
//    }
//
//    public static void load(String conf, boolean check) throws Exception {
//        BsConfig.conf = conf;
//        reload();
//        if (check) {
//            new ReloadThread().start();
//        }
//    }
//
//    public static void load(String conf) throws Exception {
//        load(conf, true);
//    }
//
//    public static void reload() throws Exception {
//        File file = new File(conf);
//        if (!file.exists()) {
//            //此处应为Warn Level
//            logger.info("File is not exists : " + conf);
//            return;
//        }
//        if (file.lastModified() <= lastModified) {
//            //此处应为Debug Level
//            logger.info("File is not modified : " + conf);
//            return;
//        }
//
//        String json = FileHelper.readFileContent(conf);
//        logger.info("Reload Config Begin:" + conf);
//        logger.info("Config:" + json);
//
//        //从HashMap中获得数据
//        //TypeToken为虚拟内部类(?)
//        data = gson.fromJson(json, new TypeToken<HashMap<String, String>>() {
//        }.getType());
//        logger.info("Reload Config End:" + conf);
//        //设定文件最后修改时间
//        lastModified = file.lastModified();
//
//    }
//
//    static class ReloadThread extends Thread {
//        public void run() {
//            while (true) {
//                try {
//                    Thread.sleep(1000);
//                    reload();
//                } catch (Exception e) {
//
//                }
//            }
//        }
//    }
//
//    public static void main(String[] args) throws Exception {
//        BsConfig.load("conf/bs.conf");
//    }
//
//}

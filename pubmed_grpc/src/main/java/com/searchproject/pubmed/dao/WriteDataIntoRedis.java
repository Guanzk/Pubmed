package com.searchproject.pubmed.dao;

import java.io.*;
import java.util.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class WriteDataIntoRedis {

    private JedisPool pool;


    /*
    非切片连接池
     */
    private JedisPool jedisPool;

    private String ip = "localhost";

    public WriteDataIntoRedis() {
        initialPool();
    }

    private void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        //是否启用后进先出，默认为true
        config.setLifo(true);
        //最大空闲连接数，默认8个
        config.setMaxIdle(10);
        //最大连接数，默认8个
        config.setMaxTotal(1000);
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(-1);
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(1800000);
        //最小空闲连接数, 默认0
        config.setMinIdle(0);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(3);
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(1800000);
        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(false);
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);

        jedisPool = new JedisPool(config, ip, 6379);
    }

    private Jedis getResource() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }

    /*
    @Description:关闭连接
     */

    private void returnResource(Jedis jedis) {
        if (jedis.isConnected()) {
            try {
                jedis.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void returnpoolResource(WriteDataIntoRedis pool) {
        Jedis jedis = pool.getResource();
        if (jedis.isConnected()) {
            try {
                jedis.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void Updateauthorjson(WriteDataIntoRedis pool, HashMap<String, String> ansMap) {
        WriteDataIntoRedis.writeStringString_pool(pool, ansMap, 75);
    }

    public static void UpdateGeneMedjson(WriteDataIntoRedis pool, HashMap<String, String> ansMap) {
        WriteDataIntoRedis.writeStringString_pool(pool, ansMap, 76);
    }


    public static <T> void writeStringString_pool(WriteDataIntoRedis pool, HashMap<String, String> ansMap, int baseNum) {
        Jedis jedis = pool.getResource();
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            String str1 = (String) var3.next();
            String str2 = ansMap.get(str1);
            jedis.set(str1, str2);

        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }


    public static void writezset(String key, HashMap<String, Integer> ansmap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansmap.keySet().iterator();
        while (var3.hasNext()) {
            String member = (String) var3.next();
            double score = ansmap.get(member);
            jedis.zadd(key, score, member);
        }
        jedis.close();
        //System.out.println("Write Data in Redis");

    }


    public static <T> void write(HashMap<String, ArrayList<T>> ansMap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            String str = (String) var3.next();
            Iterator var5 = ((ArrayList) ansMap.get(str)).iterator();

            while (var5.hasNext()) {
                T i = (T) var5.next();
                jedis.sadd(str, new String[]{i + ""});
            }
        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }

    public static void writeAuthorList(HashMap<String, ArrayList<Integer>> ansMap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            String str = (String) var3.next();
            Iterator var5 = ((ArrayList) ansMap.get(str)).iterator();

            while (var5.hasNext()) {
                int i = (int) var5.next();
                jedis.sadd(str, String.valueOf(i));
            }
        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }

    public static <T> void writeaid(HashMap<Integer, String> ansMap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            int keyy = (Integer) var3.next();

            String str = ansMap.get(keyy);
            jedis.set(String.valueOf(keyy), str);

        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }

    public static <T> void writeStringString(HashMap<String, String> ansMap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            String str1 = (String) var3.next();
            String str2 = ansMap.get(str1);
            jedis.set(str1, str2);

        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }

    public static <T> void writeauthor_aid(HashMap<String, Integer> ansMap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            String str = (String) var3.next();

            int keyy = ansMap.get(str);
            jedis.set(str, String.valueOf(keyy));

        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }

    public static <T> void writeIntInt(HashMap<Integer, Integer> ansMap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            int key1 = (Integer) var3.next();
            int value = ansMap.get(key1);
            jedis.set(String.valueOf(key1), String.valueOf(value));

        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }


    public static <T> void testconnect(HashMap<String, ArrayList<T>> ansMap, int baseNum) {
        Jedis jedis = new Jedis("localhost");
        jedis.select(baseNum);
        Iterator var3 = ansMap.keySet().iterator();

        while (var3.hasNext()) {
            String str = (String) var3.next();
            Iterator var5 = ((ArrayList) ansMap.get(str)).iterator();

            while (var5.hasNext()) {
                T i = (T) var5.next();
                jedis.lpush(str, new String[]{i + ""});
            }
        }
        jedis.close();
        System.out.println("Write HashMap in Redis");
    }


}

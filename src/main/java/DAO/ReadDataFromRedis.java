package DAO;
import java.util.*;

import Bean.AuthorInformation;
import redis.clients.jedis.*;

import java.io.Reader;

public class ReadDataFromRedis {

    /*
    非切片连接池
     */
    private JedisPool jedisPool;

    private String ip ="222.200.184.74";

    public ReadDataFromRedis(){
        initialPool();
    }

    public static List<AuthorInformation> getKeywordsFromAids(ReadDataFromRedis redis, List<AuthorInformation> res) {
        Jedis jedis =redis.getResource();
        jedis.select(35);
        for(AuthorInformation a:res){
            if(a.getAid().equals("0")){
                continue;//Todo 优化数据，数据问题
            }
            Set<Tuple>s=jedis.zrevrangeWithScores(a.getAid(),0,-1);
            List<String>keywords=new LinkedList<>();
            for(Tuple t:s){
                keywords.add(t.getElement());
            }
//            log.debug(a.getAid());
            a.setKeywords(keywords);
        }
        jedis.close();
        return res;
    }

    public static HashMap<String, List<String>> getPMIDfromaids(ReadDataFromRedis redis, List<AuthorInformation> relatedAuthors) {
        HashMap<String, List<String>>res=new HashMap<>();
        Jedis jedis =redis.getResource();

        for(AuthorInformation a:relatedAuthors){
            if(a.getAid().equals("0"))continue;//TODO 数据待优化，不然卡很久
            res.put(a.getAid(),new LinkedList<String>(){{
                addAll(jedis.smembers(a.getAid()));
            }});
        }
        jedis.close();
        return res;
    }

    public static HashMap<String, Integer> getReferencefromPMIDs(ReadDataFromRedis redis, List<String> pmids) {
        Jedis jedis = redis.getResource();
        jedis.select(13);
        HashMap<String, Integer>res=new HashMap<>();
        for(String pmid:pmids){
            String ref=jedis.get(pmid);
            res.put(pmid,ref==null?0:Integer.parseInt(ref));
        }
        jedis.close();
    return res;
    }

    private void initialPool(){
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
        config.setMinIdle(3);
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(3);
        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        config.setSoftMinEvictableIdleTimeMillis(1800000);
        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(false);
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);

        jedisPool = new JedisPool(config,ip,63790,10*1000);
    }

    private Jedis getResource(){
            Jedis jedis = jedisPool.getResource();
            return jedis;
    }

    /*
    @Description:关闭连接
     */

    private void returnResource(Jedis jedis){
        if(jedis.isConnected()){
            try{
                jedis.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void returnpoolResource(ReadDataFromRedis pool){
        Jedis jedis = pool.getResource();
        if(jedis.isConnected()){
            try{
                jedis.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }






    public static Set<String>getallDataSet(ReadDataFromRedis pool,int basenum){
        Jedis jedis = pool.getResource();
        jedis.select(basenum);
        ScanParams scanParams =new ScanParams();
        scanParams.match("*");
        scanParams.count(100000);
        Long startTime = System.currentTimeMillis();
        Set<String> reset = new HashSet<String> ();
        String scanRet = "0";
        do {
            ScanResult<String> ret = jedis.scan(scanRet, scanParams);
            scanRet = ret.getCursor();// 返回用于下次遍历的游标
            reset.addAll(ret.getResult());// 返回结果
        } while (!scanRet.equals("0"));
        System.out.println("retList size:"+reset.size());
        Long endTime = System.currentTimeMillis();
        System.out.println("using time is:" + (endTime - startTime)+"ms");
        //Set<String>result = jedis.keys("*");
        jedis.close();
        return reset;
    }


    public static Set<String> getPMIDfromFullNameAuthor_Pool(ReadDataFromRedis pool,String searchkey){

        Set<String> PMIDofFullName = ReadSetFromRedis_pool(pool,2,searchkey);
        return PMIDofFullName;

    }


    public static String getFullNameAuthorfromaid_Pool(ReadDataFromRedis pool,String searchkey){
        String FullName = ReadStringfromRedis_pool(pool,3,searchkey);
        return FullName;
    }
    public static String getaidfromFullname_Pool(ReadDataFromRedis pool,String searchkey){
        String aid = ReadStringfromRedis_pool(pool,4,searchkey);
        return aid;
    }

    public static Set getPMIDfromaid(ReadDataFromRedis pool,String searchkey){
        Set<String> PMIDofaid =ReadSetFromRedis_pool(pool,5,searchkey);
        return PMIDofaid;
    }

    public static String getPubyearFromPMID(ReadDataFromRedis pool,String searchkey){
        String PUBYEAR = ReadStringfromRedis_pool(pool,6,searchkey);
        return PUBYEAR;
    }

    public static Set<String> getAidSetFromFullname(ReadDataFromRedis pool ,String searchkey){
        Set<String> aidsetfromFullName = ReadSetFromRedis_pool(pool, 7, searchkey);
        return  aidsetfromFullName;
    }
    public static Set<String> getAidfromAffiliation(ReadDataFromRedis pool ,String searchkey){
        Set<String> PMIDofAffiliation =ReadSetFromRedis_pool(pool,8 , searchkey);
        return PMIDofAffiliation;
    }

    public static Set<String> getAffiliationfromPMID(ReadDataFromRedis pool ,String searchkey){
        Set<String> PMIDofAffiliation =ReadSetFromRedis_pool(pool,9, searchkey);
        return PMIDofAffiliation;
    }

    public static String getReferencefromPMID(ReadDataFromRedis pool,String searchkey){
        String refnum =ReadStringfromRedis_pool(pool,13, searchkey);
        return refnum;
    }

    public static Set<String>getPMIDfromEntity_Pool(ReadDataFromRedis pool,String searchkey){
        Set<String> PMIDofEntity = ReadSetFromRedis_pool(pool,19,searchkey);
        return PMIDofEntity;
    }
    public static Set<String> getTypeFromEntity(ReadDataFromRedis pool, String searchkey){
        Set<String> type = ReadSetFromRedis_pool(pool, 20, searchkey);
        return type;
    }

    public static Set<String>getKeywordsFromPMID(ReadDataFromRedis pool,String searchkey){
        Set<String> keywordofPMID = ReadSetFromRedis_pool(pool,26,searchkey);
        return keywordofPMID;
    }

    public static Set<Tuple> getKeywordsFromAid(ReadDataFromRedis pool ,String searchkey){
        Set<Tuple> keywordofaid = ReadZSetFromRedis_pool(pool,35,searchkey);
        return keywordofaid;

    }
    public static String getAuthorjsonfromkeyword(ReadDataFromRedis pool, String searchkey){
        String authorjson = ReadStringfromRedis_pool(pool, 75, searchkey);
        return authorjson;
    }

    public static String getGeneMedjsonfromkeyword(ReadDataFromRedis pool, String searchkey){
        String GMjson = ReadStringfromRedis_pool(pool, 76, searchkey);
        return GMjson;
    }
    public static Set<String> ReadSetFromRedis(int basenum, String searchkey){
        Jedis jedis = new Jedis("localhost");
        jedis.select(basenum);

        //Long size = jedis.scard(searchkey);
        Set<String> sets =  jedis.smembers(searchkey);
        jedis.close();


        return sets;
    }

    public static String ReadStringfromRedis(int basenum,String searchkey){
        Jedis jedis = new Jedis("localhost");
        jedis.select(basenum);

        String result = jedis.get(searchkey);
        jedis.close();
        return result;
    }

    public static Set<Tuple> ReadZSetFromRedis_pool(ReadDataFromRedis pool ,int basenum ,String searchkey) {
        Jedis jedis = pool.getResource();
        jedis.select(basenum);
        Set<Tuple> result=jedis.zrevrangeWithScores(searchkey,0,-1);
        jedis.close();
        return result;
    }

    public static Set<String> ReadSetFromRedis_pool(ReadDataFromRedis pool,int basenum, String searchkey){
        Jedis jedis = pool.getResource();
        jedis.select(basenum);

        //Long size = jedis.scard(searchkey);
        Set<String> sets =  jedis.smembers(searchkey);

        jedis.close();
        return sets;
    }

    public static String ReadStringfromRedis_pool(ReadDataFromRedis pool,int basenum,String searchkey){
        Jedis jedis = pool.getResource();
        jedis.select(basenum);

        String result = jedis.get(searchkey);
        jedis.close();
        return result;
    }

}

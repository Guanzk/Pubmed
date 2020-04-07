package com.searchproject.pubmed.dao;

import com.google.gson.Gson;
import com.searchproject.pubmed.config.RedisIndexConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
@Slf4j
@Component
public class RedisDao {
    @Autowired
    private StringRedisTemplate redisTemplateA;
    @Autowired
    @Qualifier("redisTemplateB")
    private StringRedisTemplate redisTemplateB;
    @Autowired
    @Qualifier("redisTemplateC")
    private StringRedisTemplate redisTemplateC;
    @Autowired
    @Qualifier("redisTemplateAi")
    private StringRedisTemplate redisTemplateAi;
    @Autowired
    RedisIndexConfig redisIndexConfig;
    /**
     * 动态切换数据库
     */
    public void setAiDataBase(int num) {
        LettuceConnectionFactory connectionFactory = (LettuceConnectionFactory) redisTemplateAi.getConnectionFactory();
        if (connectionFactory != null && num != connectionFactory.getDatabase()) {
            connectionFactory.setDatabase(num);
            redisTemplateAi.setConnectionFactory(connectionFactory);
            connectionFactory.resetConnection();
            log.debug("切换成功");
        }
    }
    public Set<String> getAidSet(String fullname){
//        setDataBase(7);

        return redisTemplateA.opsForSet().members(fullname);
    }
    public Set<String> getPmidSet(String entity){
//        setDataBase(19);
        return redisTemplateB.opsForSet().members(entity);
    }
    public Set<String> getAffiliationAids(String affiliation){
//        setDataBase(8);
        return redisTemplateC.opsForSet().members(affiliation);
    }

    public ArrayList<String> getIDList(String query, String type, String ansType) {
        ArrayList<String> keys = getFormatKeyList(query, redisIndexConfig.getMap().get("RETRIVE_" + type + "_" + ansType),
                redisIndexConfig.getMap().get("FORMAT_" + type + "_NAME"));
        log.info("RETRIEVE_" + type + "_" + ansType + "====" + "FORMAT_" + type + "_NAME");
        log.info("KeysSet:" + keys);
        ArrayList<String> iDList = getIDListByFormatKey(keys, redisIndexConfig.getMap().get("RETRIEVE_" + type + "_" + ansType));
        log.info("RETRIEVE_" + type + "_" + ansType + "||RedisNum:" + redisIndexConfig.getMap().get("RETRIEVE_" + type + "_" + ansType));
        log.info("IDList:" + iDList);
        return iDList;

    }

    private ArrayList<String> getIDListByFormatKey(ArrayList<String> keys, Integer baseNum) {
        setAiDataBase(baseNum);
        ArrayList<String>res=new ArrayList<>();
        for(String key:keys){
            res.addAll(redisTemplateAi.opsForList().range(key,0,200));
        }
        return res;
    }

    private ArrayList<String> getFormatKeyList(String query, int cleanBaseNum, int aliasBaseNum) {
        setAiDataBase(cleanBaseNum);
        ArrayList<String>res=new ArrayList<>();
        String key=redisTemplateAi.opsForValue().get(query);
        if(key==null){
            setAiDataBase(aliasBaseNum);
            String cleanQuery=query.toLowerCase().replace(" ","");
            List<String>list= redisTemplateAi.opsForList().range(cleanQuery,0,100);
            if(list.size()>0)res.addAll(list);
        }else res.add(query);

        return res;
    }

    private String getVisualStrFromRedis(String query, int baseNum){
        try{
            log.info("=====WriteVisual Redis getKey:"+ query +"==baseNum:"+baseNum);
            setAiDataBase(baseNum);
            String aiTechVisual = redisTemplateAi.opsForValue().get(query);
            aiTechVisual = aiTechVisual.substring(1,aiTechVisual.length()-1);
            return aiTechVisual;
        } catch(Exception e){
            log.info("=====WriteVisual Redis Exception:"+e.toString());
            return "\"data\":\"UNKNOWN\"";
        }
    }

    //Visual Operation Area----------------------------------------------------------------------------
    /**
     * 根据query得到离线建好的可视化索引
     * 得到刊载某个关键词的最多文章的前十名及数量
     **/
    public String getAiKeyWordsVisual(String key){
        /**
         HashMap<String, HashMap<String,Integer>> journalMap = (new ReadKeyWordVisualFromRedis()).getKeyVisualMap(key,"Journal", RedisConfig.getInt("VISUAL_KeyWordInJour"));
         HashMap<String, HashMap<String,Integer>> confMap = (new ReadKeyWordVisualFromRedis()).getKeyVisualMap(key,"Conf",RedisConfig.getInt("VISUAL_KeyWordInConf"));
         HashMap<String, HashMap<String,Integer>> countryMap = (new ReadKeyWordVisualFromRedis()).getKeyVisualMap(key,"Country",RedisConfig.getInt("VISUAL_KeyWordInCountry"));
         HashMap<String, HashMap<String,Integer>> orgMap = (new ReadKeyWordVisualFromRedis()).getKeyVisualMap(key, "Org",RedisConfig.getInt("VISUAL_KeyWordInOrg"));
         HashMap<String, HashMap<String,Integer>> indMap = (new ReadKeyWordVisualFromRedis()).getKeyVisualMap(key, "Ind",RedisConfig.getInt("VISUAL_OrgInField"));
         String graph = ReadDataFromRedis.getString(key, RedisConfig.getInt("VISUAL_KeyWord_GRAPH"));
         if(graph==null){
         graph = "{}";
         }
         Gson gson = new Gson();
         String journal = gson.toJson(journalMap);
         String conf = gson.toJson(confMap);
         String country = gson.toJson(countryMap);
         String org = gson.toJson(orgMap);
         String ind = gson.toJson(indMap);

         /*
         String visual = "\"visual\":{";
         journal = journal.substring(1,journal.length()-1);
         visual = visual + journal +",";
         conf = conf.substring(1,conf.length()-1);
         visual = visual + conf + ",";
         country = country.substring(1,country.length()-1);
         visual = visual + country +"}";
         return visual;
         */
        /**
         String visual = "{\"visual\":{";
         visual += "\"bar_Key2Jour\":{";
         journal = journal.substring(12,journal.length()-1);
         visual = visual + journal +",";

         visual = visual + "\"bar_Key2Conf\":{";
         conf = conf.substring(9,conf.length()-1);
         visual = visual + conf + ","; // + "}";

         visual = visual + "\"bar_Key2Ins\":{";
         org = org.substring(8,org.length()-1);
         visual = visual + org + ",";

         visual = visual + "\"pie_Key2Field\":{";
         ind = ind.substring(8,ind.length()-1);
         visual = visual + ind ;

         //String knowledgeGraph = "\"graph_KeyKnowledge\":{\"node\":[{\"name\":\"alexNet\",\"value\":10},{\"name\":\"vggNet\",\"value\":15},{\"name\":\"CNN\",\"value\":50},{\"name\":\"GoogleNet\",\"value\":10},{\"name\":\"ResNet\",\"value\":30},{\"name\":\"RCNN\",\"value\":10},{\"name\":\"FasteRCNN\",\"value\":10},{\"name\":\"FasterRCNN\",\"value\":20},{\"name\":\"MaskRCNN\",\"value\":10},{\"name\":\"YOLO\",\"value\":30},{\"name\":\"SSD\",\"value\":30}],\"links\":[{\"source\":\"CNN\",\"target\":\"vggNet\"},{\"source\":\"CNN\",\"target\":\"AlexNet\"},{\"source\":\"CNN\",\"target\":\"GoogleNet\"},{\"source\":\"CNN\",\"target\":\"ResNet\"},{\"source\":\"CNN\",\"target\":\"RCNN\"},{\"source\":\"CNN\",\"target\":\"FasteRCNN\"},{\"source\":\"CNN\",\"target\":\"FasterRCNN\"},{\"source\":\"CNN\",\"target\":\"MaskRCNN\"},{\"source\":\"CNN\",\"target\":\"YOLO\"},{\"source\":\"CNN\",\"target\":\"SSD\"}]}";

         String knowledgeGraph = "\"graph_KeyKnowledge\":"+graph;
         String geo = "\"geo_Key2Ins\":{\"location\":{\"Beijing\":[116.3883,39.9289],\"Shanghai\":[121.4365,31.2165],\"New York\":[-73.9249,40.5943],\"San Francisco\":[-122.4429,37.7561],\"Hong Kong\":[114.1850,22.3050],\"Longdon\":[-84.4375,39.8936],\"Seattle\":[-122.3238,47.6217],\"Singapore\":[103.8558,1.2930]},\"influence\":[{\"name\":\"Beijing\",\"value\":14900},{\"name\":\"New York\",\"value\":52800},{\"name\":\"San Francisco\",\"value\":13400},{\"name\":\"Shanghai\",\"value\":6830},{\"name\":\"Honkong\",\"value\":16100},{\"name\":\"London\",\"value\":32400},{\"name\":\"Seattle\",\"value\":5260},{\"name\":\"Singapore\",\"value\":10500}]}";
         visual = visual + "," + knowledgeGraph +","+geo;
         visual+= "}}";
         //visual = visual + "}";
         //country = country.substring(1,country.length()-1);
         //visual = visual + country +"}";
         return visual;
         **/
        String keyWordvisual = "";
        String aiTech_time = "\"aiTech_time\":{\"AllTab\":{\"title\":\"["+key+"]在学术、新闻和专利中的随时间的变化\",";
        String aiTech_expert_graph = "\"aiTech_expert_graph\":{\"AllTab\":{\"title\":\"["+key+"]专家图谱及影响力分布\",";
        String aiTech_ins_geo = "\"aiTech_ins_geo\":{\"AllTab\":{\"title\":\"["+key+"]地理分布\",";
        String aiTech_entity = "\"aiTech_entity\":{\"AllTab\":{\"title\":\"["+key+"]Entity\",";
        String aiTech_field_pie = "\"aiTech_field_pie\":{\"AllTab\":{\"title\":\"["+key+"]在行业中的分布\",";

        aiTech_time += getVisualStrFromRedis(key, redisIndexConfig.getMap().get("VISUAL_aiTech_time"));
        aiTech_expert_graph += getVisualStrFromRedis(key, redisIndexConfig.getMap().get("VISUAL_aiTech_expert_graph"));
        aiTech_ins_geo += getVisualStrFromRedis(key, redisIndexConfig.getMap().get("VISUAL_aiTech_ins_geo"));
        aiTech_entity += getVisualStrFromRedis(key, redisIndexConfig.getMap().get("VISUAL_aiTech_entity"));
        aiTech_field_pie += getVisualStrFromRedis(key, redisIndexConfig.getMap().get("VISUAL_aiTech_field_pie"));

        keyWordvisual += aiTech_time +"}},";
        keyWordvisual += aiTech_expert_graph +"}},";
        keyWordvisual += aiTech_ins_geo +"}},";
        keyWordvisual += aiTech_entity +"}},";
        keyWordvisual += aiTech_field_pie +"}}";

        log.info("=========keyWordvisual:"+keyWordvisual);
        return keyWordvisual;
    }
    /**
     * 根据行业得到离线建好的可视化索引
     * 得到时间轴数据
     **/
    public String getAiFieldVisual(String key){
        /***
         String journal = (new ReadFieldVisualFromRedis()).getFieldVisualMap(key, RedisConfig.getInt("VISUAL_FieldInJourTimeLine"));
         String conf = (new ReadFieldVisualFromRedis()).getFieldVisualMap(key,RedisConfig.getInt("VISUAL_FieldInConfTimeLine"));
         String org = (new ReadFieldVisualFromRedis()).getFieldVisualMap(key, RedisConfig.getInt("VISUAL_FieldInOrgTimeLine"));

         String visual = "\"barTime_Field2Jour\":{";
         System.out.println("========"+journal+"===========");
         journal = journal.substring(1,journal.length());
         visual = visual + journal +",";


         visual = visual + "\"barTime_Field2Conf\":{";
         conf = conf.substring(1,conf.length());
         visual = visual + conf  + ",";


         visual = visual + "\"barTime_Field2Ins\":{";
         org = org.substring(1,org.length()-1);
         //visual = visual + org + "}";
         visual = visual + org + "},";

         String geo = "\"geo_Field2Ins\":{\"location\":{\"California\":[-79.9152,40.0692],\"Paris\":[-89.6925,39.6148],\"Harbin\":[126.65,45.7500],\"Tokyo\":[139.7514,35.6850],\"HangZhou\":[120.1700,35.2500],\"Beijing\":[116.3883,39.9289],\"Shanghai\":[121.4365,31.2165],\"New York\":[-73.9249,40.5943],\"San Francisco\":[-122.4429,37.7561],\"Hong Kong\":[114.1850,22.3050],\"Longdon\":[-84.4376,39.8936],\"Seattle\":[-122.3238,47.6217],\"Singapore\":[103.8558,1.2930]},\"influence\":[{\"name\":\"Beijing\",\"value\":635},{\"name\":\"New York\",\"value\":326},{\"name\":\"San Francisco\",\"value\":124},{\"name\":\"Shanghai\",\"value\":340},{\"name\":\"Honkong\",\"value\":290},{\"name\":\"London\",\"value\":400},{\"name\":\"Seattle\",\"value\":90},{\"name\":\"Singapore\",\"value\":70},{\"name\":\"California\",\"value\":290},{\"name\":\"Paris\",\"value\":40},{\"name\":\"Harbin\",\"value\":60},{\"name\":\"Tokyo\",\"value\":140},{\"name\":\"HangZhou\",\"value\":163}]}";

         String knowledgeGraph = "\"graph_FieldKnowledge\":{\"node\":[{\"name\":\"ARIMA\",\"value\":10},{\"name\":\"VAR\",\"value\":10},{\"name\":\"Deep Regression\",\"value\":10},{\"name\":\"CNN\",\"value\":10},{\"name\":\"LSTM\",\"value\":10},{\"name\":\"Finance\",\"value\":50}],\"links\":[{\"source\":\"Finance\",\"target\":\"ARIMA\"},{\"source\":\"Finance\",\"target\":\"VAR\"},{\"source\":\"Finance\",\"target\":\"LSTM\"},{\"source\":\"Finance\",\"target\":\"Deep Regression\"},{\"source\":\"Finance\",\"target\":\"CNN\"},{\"source\":\"Finance\",\"target\":\"LSTM\"}]}";
         visual = visual + geo + "," + knowledgeGraph;
         return visual;
         **/

        String fieldVisual = "";

        String field_expert_graph = "\"field_expert_graph\":{\"AllTab\":{\"title\":\"["+key+"行业]专家合作网络\",";
        String field_aiTech_radar = "\"field_aiTech_radar\":{\"AllTab\":{\"title\":\"["+key+"行业]技术雷达图\",";

        field_expert_graph += getVisualStrFromRedis(key, redisIndexConfig.getMap().get("VISUAL_field_expert_graph"));
        field_aiTech_radar += getVisualStrFromRedis(key, redisIndexConfig.getMap().get("VISUAL_field_aiTech_radar") );

        fieldVisual += field_expert_graph+"}},";
        fieldVisual += field_aiTech_radar+"}}";
        return fieldVisual;
    }
    /**
     * 根据query得到离线建好的可视化索引
     * 得到作者的可视化数据
     **/
    public String getAiExpertVisual(String author){
        String technology = getTechnologyOfAuthor(author);

        HashMap<String, HashMap<String,Integer>> fieldMap = getAuthVisualMap(author,"Field");
        HashMap<String, HashMap<String,Integer>> journalMap = getAuthVisualMap(author,"Jour");
        HashMap<String, HashMap<String,Integer>> confMap = getAuthVisualMap(author,"Conf");
        HashMap<String, HashMap<String,Integer>> orgMap = getAuthVisualMap(author,"Org");
        HashMap<String, HashMap<String,Integer>> coopMap = getAuthVisualMap(author,"Coop");

        Gson gson = new Gson();
        String field = gson.toJson(fieldMap);
        String journal = gson.toJson(journalMap);
        String conf = gson.toJson(confMap);
        String org = gson.toJson(orgMap);
        String coop = gson.toJson(coopMap);

        String visual = "\"visual\":{";

        visual = visual + technology +",";
        field = field.substring(1,field.length()-1);
        visual = visual + field +",";
        journal = journal.substring(1,journal.length()-1);
        visual = visual + journal +",";
        conf = conf.substring(1,conf.length()-1);
        visual = visual + conf + ",";
        org = org.substring(1,org.length()-1);
        visual = visual + org + ",";
        coop = coop.substring(1,coop.length()-1);
        visual = visual + coop+"}";
        return visual;
    }

    public String getAiOrgVisual(String org){
        /**
         try {
         org = toUpperFirstCode(org);
         HashMap<String, HashMap<String, Integer>> authorMap = (new ReadOrgVisualFromRedis()).getOrgVisualMap(org, "bar_Ins2Exp");
         HashMap<String, HashMap<String, Integer>> confMap = (new ReadOrgVisualFromRedis()).getOrgVisualMap(org, "bar_Ins2Conf");
         HashMap<String, HashMap<String, Integer>> industryMap = (new ReadOrgVisualFromRedis()).getOrgVisualMap(org, "pie_Ins2Field");
         //	HashMap<String, HashMap<String,Integer>> techMap = ReadDataFromRedis.getOrgVisualMap(org,"bar_Ins2Tech");
         Gson gson = new Gson();
         String author = gson.toJson(authorMap);
         String conf = gson.toJson(confMap);
         String industry = gson.toJson(industryMap);
         //	String tech = gson.toJson(techMap);
         String tech = (new ReadOrgVisualFromRedis()).getOrgTimeline(org);
         String visual = "{\"visual\":{";
         //String visual = "\"visual\":{";
         author = author.substring(1, author.length() - 1);
         visual = visual + author + ",";
         conf = conf.substring(1, conf.length() - 1);
         visual = visual + conf + ",";
         industry = industry.substring(1, industry.length() - 1);
         visual = visual + industry + ",";
         tech = tech.substring(1, tech.length() - 1);
         visual = visual + "\"barTime_Ins2Tech\":{";
         visual = visual + tech + "}}}";
         return visual;
         } catch( Exception exception){
         return "";
         }
         **/
        String orgVisual = "";
        String ins_expert_influence_bar = "\"ins_expert_influence_bar\":{\"AllTab\":{\"title\":\"专家及影响力在"+org+"中的分布\",";
        String ins_aiTech_themeRiver = "\"ins_aiTech_themeRiver\":{\"AllTab\":{\"title\":\""+org+" 技术演化\",";
        String ins_field_pie = "\"ins_field_pie\":{\"AllTab\":{\"title\":\""+org+" 的领域分布情况\",";
        String ins_field_time_pieline = "\"ins_field_time_pieline\":{\"AllTab\":{\"title\":\""+org+" 饼图 折线图\",";


        ins_expert_influence_bar += getVisualStrFromRedis(org, redisIndexConfig.getMap().get("VISUAL_ins_expert_influence_bar"));
        ins_aiTech_themeRiver += getVisualStrFromRedis(org, redisIndexConfig.getMap().get("VISUAL_ins_aiTech_themeRiver"));
        ins_field_pie += getVisualStrFromRedis(org, redisIndexConfig.getMap().get("VISUAL_ins_field_pie"));
        ins_field_time_pieline += getVisualStrFromRedis(org, redisIndexConfig.getMap().get("VISUAL_ins_field_time_pipeline"));

        orgVisual += ins_expert_influence_bar +"}},";
        orgVisual += ins_aiTech_themeRiver +"}},";
        orgVisual += ins_field_pie+"}},";
        orgVisual += ins_field_time_pieline +"}}";


        log.info("====ins_expert_influence_bar:"+ins_expert_influence_bar);
        log.info("====orgVisual:"+orgVisual);
        return orgVisual;
    }
    //Expert领域自定义方法 -> 获取专家的可视化索引
    public HashMap<String, HashMap<String, Integer>> getAuthVisualMap(String auth, String type){
        setAiDataBase(selectRedisDatabase(type));
        HashMap<String, Integer> visualMap = new HashMap<String, Integer>();
        for(String record : redisTemplateAi.opsForZSet().range(auth,0,-1)){
            //跳过被搜索的作者自身
            if(auth.equals(record.toLowerCase())) {
                continue;
            }
            visualMap.put(record.trim(), redisTemplateAi.opsForZSet().score(auth,record).intValue());
        }
        HashMap<String, HashMap<String, Integer>> authVisualMap = new HashMap<String, HashMap<String, Integer>>();
        if(type.equals("Coop")){
            authVisualMap.put("graph_Exp2"+type,visualMap);
        } else {
            authVisualMap.put("bar_Exp2" + type, visualMap);
        }
        return authVisualMap;
    }

    public String getTechnologyOfAuthor(String auth) {
        setAiDataBase(11);
        String jsonString = "\"barTime_Exp2Tech\":{";

        for(Object record : redisTemplateAi.opsForHash().keys(auth)) {
            jsonString += "\"" + record + "\":" + redisTemplateAi.opsForHash().get(auth, record);
            jsonString += ",";
        }

        char lastChar = jsonString.charAt(jsonString.length()-1);
        if(lastChar == ',') {
            jsonString = jsonString.substring(0, jsonString.length() - 1);
        }
        jsonString += "}";
        return jsonString;
    }

    public int selectRedisDatabase(String type) {
        int baseNum = 0;

        if(type.equals("Jour")){
            baseNum = redisIndexConfig.getMap().get("VISUAL_Expert_Journal");
        }
        else if(type.equals("Conf")){
            baseNum = redisIndexConfig.getMap().get("VISUAL_Expert_Conf");
        }
        else if(type.equals("Org")){
            baseNum = redisIndexConfig.getMap().get("VISUAL_Expert_Org");
        }
        else if(type.equals("Field")){
            baseNum = redisIndexConfig.getMap().get("VISUAL_Expert_Field");
        }
        else if(type.equals("Coop")){
            baseNum = redisIndexConfig.getMap().get("VISUAL_Expert_Coop");
        }

        return baseNum;
    }
}

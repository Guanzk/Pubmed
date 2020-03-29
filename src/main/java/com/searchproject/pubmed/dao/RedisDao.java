package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.config.RedisIndexConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        ArrayList<String>keys=getFormatKeyList(query,redisIndexConfig.getMap().get("RETRIVE_"+type+"_"+ansType),
                redisIndexConfig.getMap().get("FORMAT_"+type+"_NAME"));
        log.info("RETRIEVE_"+type+"_"+ansType+"===="+"FORMAT_"+type+"_NAME");
        log.info("KeysSet:"+keys);
        ArrayList<String> iDList = getIDListByFormatKey(keys,redisIndexConfig.getMap().get("RETRIEVE_"+type+"_"+ansType));
        log.info("RETRIEVE_"+type+"_"+ansType+"||RedisNum:"+redisIndexConfig.getMap().get("RETRIEVE_"+type+"_"+ansType));
        log.info("IDList:"+iDList);
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
            List<String>list= redisTemplateAi.opsForList().range(query,0,100);
            if(list.size()>0)res.addAll(list);
        }else res.add(query);

        return res;
    }
}

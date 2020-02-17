package com.searchproject.pubmed.service;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.searchproject.pubmed.Bean.*;
import com.searchproject.pubmed.dao.MongoDao;
//import com.searchproject.pubmed.dao.ReadDataFromRedis;
import com.searchproject.pubmed.dao.RedisDao;
import com.searchproject.pubmed.util.ExpertHelper;
import com.searchproject.pubmed.util.MakeJson;
//import com.searchproject.pubmed.util.QueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
@Component
@Slf4j
public class SearchPubmed{
@Autowired
    RedisDao redisDao;
//@Autowired
//QueryResult queryResult;
@Autowired
    MongoDao mongoDao;
//@Deprecated
//    public  String processPubmedSearch(String query) {
//        log.info("———MessageReseived Function Process—-——————-");
//        long startTime = System.currentTimeMillis();
//        log.info("Query:" + query);
//        query = query.toLowerCase();
//        Author author = new Author();
//        ReadDataFromRedis redis = new ReadDataFromRedis();
//        Set<String> aid = ReadDataFromRedis.getAidSetFromFullname(redis, query);
//        Set<String> entityPMIDS = ReadDataFromRedis.getPMIDfromEntity_Pool(redis, query);//TODO 有可能pmid太多
//       String result="";
//
//
//        if (aid != null && aid.size() > 0) {
//            log.info("aids:" + aid);//同名策略，待扩充
//            List<String> aids = new ArrayList<>();
//            aids.addAll(aid);
//            aids.remove("0");
//            List<String>usefulAids=ReadDataFromRedis.findUsefulAuthors(redis,aids);
//            if (usefulAids.size() == 0) {
//                result="not found author";
//
//            } else {//改进，判断作者有无pmid
//                log.info("search:" + usefulAids.get(0));
//                author = queryResult.processAuhtor(usefulAids.get(0));
//                String authorJson = MakeJson.makeAuthorJson(author);
//                log.info(authorJson);
//                result=authorJson;
//            }
//
//        } else if (!entityPMIDS.isEmpty()) {
//            log.info("开始查找医药实体");
//            MedEntity entity = queryResult.processMedEntity(query);
//            String entityJson = MakeJson.makeEntityJson(entity);
//            log.info("entity json:" + entityJson);
//            result=entityJson;
//
//        } else if (aid == null) {
//            log.info("can not find query");
//            result="not found query";
//        }
//        long finishQueryTime = System.currentTimeMillis();
//        log.info("process time:" + (finishQueryTime - startTime));
//
//        return result;
//    }
    //使用MOngo
    public  String processPubmedQuery(String query) {
        log.info("———MessageReseived Function Process—-——————-");
        log.debug("use mongo database");
        long startTime = System.currentTimeMillis();
        log.info("Query:" + query);
        query = query.toLowerCase();
        Set<String> aid = redisDao.getAidSet(query);
        Set<String> entityPMIDS = redisDao.getPmidSet(query);
        String result="";


        if (aid != null && aid.size() > 0) {
            log.info("aids:" + aid);
            aid.remove("0");
            List<String>aids=new ArrayList<>(aid);
            if(aids.size()==1){
                result=searchAid(aids.get(0));
            }else{
                List<ExpertMongo> experts=mongoDao.getExperts(aids);
                result=MakeJson.getMultiExpertJson(experts);
            }

        } else if (!entityPMIDS.isEmpty()) {
            log.info("开始查找医药实体");
            EntityMongo entity=mongoDao.getEntity(query);//TODO 相似名字提醒
            String entityJson = MakeJson.getEntityJson(entity);
            log.info("entity json:" + entityJson);
            result=entityJson;

        } else {
            log.info("can not find query");
            result="not found query";
        }
        long finishQueryTime = System.currentTimeMillis();
        log.info("process time:" + (finishQueryTime - startTime));

        return result;
    }
//    private HashMap<String, Integer> pubyearCount(List<Article> articles) {
//        HashMap<String, Integer> res = new HashMap<>();
//        for (Article article : articles) {
//            String pubYear = article.getPubyear();
//            if (res.containsKey(pubYear)) {
//                res.put(pubYear, res.get(pubYear) + 1);
//            } else {
//                res.put(pubYear, 1);
//            }
//        }
//        return res;
//    }
    Gson gson= new GsonBuilder().create();
    public String searchAid(String aid) {
        log.info("search aid:"+aid);
        long start=System.currentTimeMillis();


        ExpertMongo expert=mongoDao.getExpert(Long.parseLong(aid));
        log.debug(gson.toJson(expert));
        List<String>relatedAids= ExpertHelper.getRelatedAids(expert);
        relatedAids.remove("0");
        log.info("size:"+relatedAids.size()+gson.toJson(relatedAids));
        List<ExpertMongo>relatedExperts=mongoDao.getExperts(relatedAids);
        log.debug(gson.toJson(relatedExperts));
        String result=MakeJson.getExpertJson(expert,relatedExperts);
        long end=System.currentTimeMillis();
        log.debug(result);
        log.info("用时:"+(end-start));
        return result;
    }
}

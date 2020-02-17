package com.searchproject.pubmed.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.searchproject.pubmed.Bean.ExpertMongo;
import com.searchproject.pubmed.dao.MongoDao;
import com.searchproject.pubmed.dao.RedisDao;
import com.searchproject.pubmed.service.SearchPubmed;
import com.searchproject.pubmed.util.ExpertHelper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestRedis {
    @Autowired
    RedisDao redisDao;
    @Autowired
    SearchPubmed searchPubmed;
    @Autowired
    MongoDao mongoDao;
    Gson gson=new GsonBuilder().create();
    @Test
    public void test(){
        log.debug("测试开始");

//        redisDao.getAidSet("web1");
        log.info(gson.toJson(redisDao.getAidSet("j a siler")));
        log.info(gson.toJson(redisDao.getPmidSet("arch coarctation of the aorta")));
        log.info(gson.toJson(redisDao.getAffiliationAids("Ecole Pratique des Hautes Etudes, UMII, Montpellier, France.")));
    }
    @Test
    public void testQuery(){
//        log.info(searchPubmed.searchAid("1763228"));
//        log.info(searchPubmed.searchAid("1763228"));//6064464,6474403
        String aid="23";
        String name="v peterson";
//        log.info(searchPubmed.processPubmedQuery(name));
        log.info(searchPubmed.searchAid(aid));

//        ExpertMongo expert=mongoDao.getExpert(Long.parseLong(aid));
//
//        List<String> relatedAids= ExpertHelper.getRelatedAids(expert);
//        for(String a:relatedAids){
//            log.info(a);
//            log.info(gson.toJson(mongoDao.getExpert(Long.parseLong(a))));
//        }
//        log.info(searchPubmed.processPubmedQuery("K Hayashi"));
//        log.info(gson.toJson(mongoDao.getExpert(Long.parseLong(aid))));
    }
    @Test
    public void testEntity(){
        String entity="rabit";

        log.info(searchPubmed.processPubmedQuery(entity));
    }
}

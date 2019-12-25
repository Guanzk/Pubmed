package com.searchproject.pubmed.test;

import com.searchproject.pubmed.Bean.AffiliationCount;
import com.searchproject.pubmed.Bean.Article;
import com.searchproject.pubmed.Bean.AuthorSimple;
import com.searchproject.pubmed.Bean.Paper;
import com.searchproject.pubmed.dao.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDao {
//    @Autowired
//    PaperDao paperDao;
//    @Autowired
//    AffiliationCountDao affiliationCountDao;
//    @Autowired
//    AuthorSimpleDao authorSimpleDao;
//    @Autowired
//    ArticleSimpleDao articleSimpleDao;
@Autowired
MongoUserRepository mongoUserRepository;
    @Test
    public void test(){
        ArticleMongo u=mongoUserRepository.findByPmid(622);
        System.out.println(u);
//        ReadDataFromRedis redis=new ReadDataFromRedis();
//        String query="u kissel";
//        List<String> aid =new ArrayList<>(ReadDataFromRedis.getAidSetFromFullname(redis, query));
//        Set<String> entityPMIDS = ReadDataFromRedis.getPMIDfromEntity_Pool(redis, query);
////        List<AuthorSimple>as=authorSimpleDao.findAllByAidIn(new ArrayList<>(aid));
//        List<String>pmids=new ArrayList<>(ReadDataFromRedis.getPMIDfromaid(redis,aid.get(0)));
//        log.info("pmids: "+pmids);
////        List<Article>articles=articleSimpleDao.findAllByPmidIn(pmids);
//////        log.info("authorSimpleDao.findAllByAidIn:"+as.size());
////        log.info("articles size:"+articles.size());
////        List<String>affiliations=new ArrayList<>(ReadDataFromRedis.getAffiliationfromPMID(redis,pmids.get(0)));
////        log.info("affiliations:"+affiliations);
////        List<AffiliationCount> year=affiliationCountDao.findAllByAffiliationIn(affiliations);
////        log.info("affiliation count:"+year);
//        List<AuthorSimple>as=authorSimpleDao.findAllByPmidIn(pmids);
//        log.info("authors:"+as);

//        List<Paper> ps=paperDao.findAll(ex);
//        System.out.println(ps);
    }
}

package com.searchproject.pubmed.test;

import com.searchproject.pubmed.Bean.ExpertMongo;
import com.searchproject.pubmed.dao.MongoExpertRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    MongoExpertRepository mongoExpertRepository;
    @Test
    public void test(){
        ExpertMongo expert=mongoExpertRepository.findByAid(2756153);
        log.info(expert.getAffiliations().get(0).getAuthors().get(0));

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

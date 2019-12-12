package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.AffiliationCount;
import com.searchproject.pubmed.Bean.AuthorInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class ArticleUtil {
    private static ReadDataFromRedis redis = new ReadDataFromRedis();
@Autowired
AffiliationCountDao affiliationCountDao;
    public static HashMap<String, List<String>> getAffiliations(List<String> pmids) {
        HashMap<String, List<String>> res = new HashMap<>();
        for (String pmid : pmids) {//TODO 待改
            Set<String> affiliations = ReadDataFromRedis.getAffiliationfromPMID(redis, pmid);
            for (String affiliation : affiliations) {
                if (!res.containsKey(affiliation)) {
                    List<String> l = new LinkedList<>();
                    l.add(pmid);
                    res.put(affiliation, l);
                } else {
                    res.get(affiliation).add(pmid);
                }
            }
        }
        return res;
    }

    public HashMap<String, Integer> getAffiliationTotalPapers(Set<String> keySet) {
        HashMap<String, Integer> res = new HashMap<>();
        long start = System.currentTimeMillis();
        List<AffiliationCount>affiliationCounts=affiliationCountDao.findAllByAffiliationIn(new ArrayList<>(keySet));
        for(AffiliationCount affiliationCount:affiliationCounts){
            res.put(affiliationCount.getAffiliation(),affiliationCount.getTotal_count());
        }
//        res = (HashMap<String, Integer>) ReadArticleFromMySQL.getAffiliationsTotalPapers(new LinkedList<String>() {{
//            addAll(keySet);
//        }});
        long end = System.currentTimeMillis();
        log.debug("获取机构总文章数用时:" + (end - start));
        return res;
    }

    public static HashMap<String, List<String>> getPmidByAid(List<AuthorInformation> relatedAuthors) {

        long start = System.currentTimeMillis();
        HashMap<String, List<String>> res = ReadDataFromRedis.getPMIDfromaids(redis, relatedAuthors);

        long end = System.currentTimeMillis();
        log.debug("获取文章aid用时:" + (end - start));
        return res;
    }

    public static HashMap<String, Integer> getPmidCitation(List<String> pmids) {

        long start = System.currentTimeMillis();
        HashMap<String, Integer> res = ReadDataFromRedis.getReferencefromPMIDs(redis, pmids);
        long end = System.currentTimeMillis();

        log.debug("获取文章引用用时:" + (end - start));
        return res;
    }
}

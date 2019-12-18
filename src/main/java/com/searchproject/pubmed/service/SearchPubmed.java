package com.searchproject.pubmed.service;



import com.searchproject.pubmed.Bean.Article;
import com.searchproject.pubmed.Bean.Author;
import com.searchproject.pubmed.Bean.AuthorInformation;
import com.searchproject.pubmed.Bean.MedEntity;
import com.searchproject.pubmed.dao.ReadAuthorFromMySQL;
import com.searchproject.pubmed.dao.ReadDataFromRedis;
import com.searchproject.pubmed.util.MakeJson;
import com.searchproject.pubmed.util.QueryResult;
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
QueryResult queryResult;
    public  String processPubmedSearch(String query) {
        log.info("———MessageReseived Function Process—-——————-");
        long startTime = System.currentTimeMillis();
        log.info("Query:" + query);
        query = query.toLowerCase();
        Author author = new Author();
        ReadDataFromRedis redis = new ReadDataFromRedis();
        Set<String> aid = ReadDataFromRedis.getAidSetFromFullname(redis, query);
        Set<String> entityPMIDS = ReadDataFromRedis.getPMIDfromEntity_Pool(redis, query);
       String result="";


        if (aid != null && aid.size() > 0) {
            log.info("aids:" + aid);//同名策略，待扩充
            List<String> aids = new ArrayList<>();
            aids.addAll(aid);
            aids.remove("0");
            List<String>usefulAids=ReadDataFromRedis.findUsefulAuthors(redis,aids);
            if (usefulAids.size() == 0) {
                result="not found author";

            } else {//改进，判断作者有无pmid
                log.info("search:" + usefulAids.get(0));
                author = queryResult.processAuhtor(usefulAids.get(0));
                String authorJson = MakeJson.makeAuthorJson(author);
                log.info(authorJson);
                result=authorJson;
            }

        } else if (!entityPMIDS.isEmpty()) {
            log.info("开始查找医药实体");
            MedEntity entity = queryResult.processMedEntity(query);
            String entityJson = MakeJson.makeEntityJson(entity);
            log.info("entity json:" + entityJson);
            result=entityJson;

        } else if (aid == null) {
            log.info("can not find query");
            result="not found query";
        }
        long finishQueryTime = System.currentTimeMillis();
        log.info("process time:" + (finishQueryTime - startTime));

        return result;
    }

    private HashMap<String, Integer> pubyearCount(List<Article> articles) {
        HashMap<String, Integer> res = new HashMap<>();
        for (Article article : articles) {
            String pubYear = article.getPubyear();
            if (res.containsKey(pubYear)) {
                res.put(pubYear, res.get(pubYear) + 1);
            } else {
                res.put(pubYear, 1);
            }
        }
        return res;
    }
}

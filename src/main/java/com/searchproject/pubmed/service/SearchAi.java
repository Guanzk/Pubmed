package com.searchproject.pubmed.service;

import com.searchproject.pubmed.dao.RedisDao;
import com.searchproject.pubmed.util.AiMatchQuery;
import com.searchproject.pubmed.util.QueryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public class SearchAi {
    @Autowired
    AiMatchQuery matchQuery;
    @Autowired
    RedisDao redisDao;
    public String search(String query) {
        query=query.toLowerCase();
        String resultJson="";
        List<QueryClass>queryClasses=matchQuery.ngramMatch(query);
        HashMap<String,String> result=new HashMap<>();
        for(int i=0;i<queryClasses.size();i++){
            result.put(queryClasses.get(i).getMaxQuery(),queryClasses.get(i).getQueryType());
        }
        if(queryClasses.isEmpty()){
            result.put(query,"keyWord");
        }
        LinkedList<String>keyList=new LinkedList<>(result.keySet());
        ArrayList<ArrayList<String>> allPaperID  = new ArrayList<>();
        ArrayList<ArrayList<String>> allNewsID = new ArrayList<>();
        ArrayList<ArrayList<String>> allPatentID = new ArrayList<>();
        ArrayList<ArrayList<String>> allCNNewsID = new ArrayList<>();
        for(String k:keyList){
            allPaperID.add(redisDao.getIDList(k,result.get(k),"Paper"));
            allNewsID.add(redisDao.getIDList(k,result.get(k), "News"));
            allPatentID.add(redisDao.getIDList(k, result.get(k), "Patent"));
            allCNNewsID.add(redisDao.getIDList(k, result.get(k), "CNNews"));
        }

        return resultJson;
    }
}

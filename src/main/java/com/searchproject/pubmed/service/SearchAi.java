package com.searchproject.pubmed.service;

import com.searchproject.pubmed.Bean.CnNews;
import com.searchproject.pubmed.Bean.News;
import com.searchproject.pubmed.Bean.Paper;
import com.searchproject.pubmed.Bean.Patent_new;
import com.searchproject.pubmed.dao.MysqlDao;
import com.searchproject.pubmed.dao.RedisDao;
import com.searchproject.pubmed.util.AiMatchQuery;
import com.searchproject.pubmed.util.MakeJson;
import com.searchproject.pubmed.util.QueryClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SearchAi {
    @Autowired
    AiMatchQuery matchQuery;
    @Autowired
    RedisDao redisDao;
    @Autowired
    MysqlDao mysqlDao;
    public String search(String query) {
        query=query.toLowerCase();
        String resultJson="";
        List<QueryClass>queryClasses=matchQuery.ngramMatch(query);
        HashMap<String,String> result=new HashMap<>();
        for(int i=0;i<queryClasses.size();i++){
            result.put(queryClasses.get(i).getMaxQuery(),queryClasses.get(i).getQueryType());
        }
        if(queryClasses.isEmpty()){
            result.put(query,"KeyWord");
        }
        log.info("---HashMap<> Result: " + result);
        LinkedList<String> keyList=new LinkedList<>(result.keySet());
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

        ArrayList<Paper> paperResult = new ArrayList<>();
        ArrayList<CnNews> cnnewsResult = new ArrayList<>();
        ArrayList<News> newsResult = new ArrayList<>();
        ArrayList<Patent_new> patentResult = new ArrayList<>();
        log.info("{}",allPaperID);
        if(!allPaperID.isEmpty()) {
            for(ArrayList<String> paperID : allPaperID) {
                if(paperID.isEmpty()){
                    continue;
                }
                paperResult.addAll(mysqlDao.getPapers(paperID));
            }
        }
        if(!allCNNewsID.isEmpty()) {
            for(ArrayList<String> cnnewsID : allCNNewsID) {
                if(cnnewsID.isEmpty()){
                    continue;
                }
                cnnewsResult.addAll(mysqlDao.getCnnews(cnnewsID));
            }
        }
        if(!allNewsID.isEmpty()) {
            for(List<String> newsID : allNewsID) {
                //由于传入的ArrayList为String属性，因而为自动配置方便，在此需要对数组属性进行转换 String -> Integer
                List<Integer> newsid = newsID.stream().map(Integer::parseInt).collect(Collectors.toList());
                if(newsID.isEmpty()){
                    continue;
                }
                newsResult.addAll(mysqlDao.getNews(newsid));
            }
        }
        if(!allPatentID.isEmpty()) {
            for(ArrayList<String> patentID : allPatentID) {
                if(patentID.isEmpty()){
                    continue;
                }
                patentResult.addAll(mysqlDao.getPatents(patentID));
            }
        }
        //将查询结果转换为JSON格式
        String retrieveAnswer = MakeJson.getHarfAiJson(paperResult, cnnewsResult, newsResult, patentResult);
        //记录关键词
        String visualQuery = getAiVisualQueryAndType(result).get(0);
        //记录属性字段，只把第一个拿出来
        String visualType = getAiVisualQueryAndType(result).get(1);
        log.info("=============visualQuery:"+visualQuery);
        log.info("=============visualType:"+visualType);
        //从redis获得对应搜素类型关键词相关的数据
        String visualAnswer = getAiVisualAns(visualQuery,visualType);
        log.info("visualAnswer:"+visualAnswer);
        //把得到的结果和关键词相关的数据写为json
        resultJson = MakeJson.getAiFinalAnswer(retrieveAnswer,visualAnswer);

        return resultJson;
    }
    //记录关键词，并匹配所得Query的属性
    private ArrayList<String> getAiVisualQueryAndType(HashMap<String,String> queryAndType) {
        ArrayList<String> ans = new ArrayList<>();
        for(String str : queryAndType.keySet()){
            if(queryAndType.get(str).equals("Field")){
                ans.add(str);
                ans.add("Field");
                return ans;
            }else if(queryAndType.get(str).equals("Org")){
                ans.add(str);
                ans.add("Org");
                return ans;
            }else if(queryAndType.get(str).equals("KeyWord")){
                ans.add(str);
                ans.add("KeyWord");
                return ans;
            }else if(queryAndType.get(str).equals("Expert")){
                ans.add(str);
                ans.add("Expert");
                return ans;
            }else{
                ans.add(str);
                ans.add("KeyWord");
                return ans;
            }
        }
        ans.add("deep learning");
        ans.add("KeyWord");
        return ans;
    }
    //从Redis中获得与搜索Query相匹配的关键词数据 -> 方法定义如下（属于集成方法：子方法由于需要对Redis进行实际操控，放在RedisDao中）
    public String getAiVisualAns(String query, String queryType){
        String visual = "";

        if(queryType == "KeyWord"){
            String keyWordVisual = redisDao.getAiKeyWordsVisual(query);
            visual += "{\"visual\":{"+keyWordVisual+"}";
            return visual;
        }else if(queryType == "Field"){
            String fieldVisual = redisDao.getAiFieldVisual(query);
            visual += "{\"visual\":{"+fieldVisual+"}";
            return visual ;
        }else if(queryType == "Expert"){
            String expertVisual = redisDao.getAiExpertVisual(query);
            visual += "{\"visual\":{"+expertVisual+"}";
            return visual;
        }else if(queryType == "Org"){
            String orgVisual = redisDao.getAiOrgVisual(query);
            visual += "{\"visual\":{"+orgVisual+"}";
            return visual;
        }else{
            return "{\"visual\":\"WrongQueryType\"}";
        }
    }

}

package com.searchproject.pubmed.util;

import com.searchproject.pubmed.Bean.ExpertMongo;
import com.searchproject.pubmed.Bean.auxiliary.AffiliationSimpleMongo;
import com.searchproject.pubmed.Bean.auxiliary.KeywordCount;
import com.searchproject.pubmed.Bean.auxiliary.PmidMongo;
import com.searchproject.pubmed.Bean.json.ExpertDistributionData;

import java.util.*;
import java.util.stream.Collectors;

public class ExpertHelper {
    public static List<String>getRelatedAids(ExpertMongo expert){
        Set<String> res=new HashSet<>();
        for(AffiliationSimpleMongo affiliation:expert.getAffiliations()){
            res.addAll(affiliation.getAuthors());
        }
        return new ArrayList<>(res);
    }

    public static List<String> getTopKKeywords(int k, ExpertMongo expert) {
        HashMap<String,Integer>keywordCount=new HashMap<>();
        for(KeywordCount keywords:expert.getAll_keywords()){
            for(String key:keywords.getKeywords()){
                if(!keywordCount.containsKey(key))keywordCount.put(key,0);
                keywordCount.put(key,keywordCount.get(key)+1);
            }
        }
        Map<String, Integer> sortedMap=
                keywordCount.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.<String, Integer>comparingByValue())).
                        collect(Collectors.toMap(e->e.getKey(), e->e.getValue(),(v1, v2)->v2,LinkedHashMap::new));
        List<String>res=new ArrayList<>();
        for(String key:sortedMap.keySet()){
            res.add(key);
            k--;
            if(k==0)break;
        }
        return res;
    }

    public static List<String> getKNames(int i,List<ExpertMongo> relatedExpert) {
        List<String>names=new ArrayList<>(i);
        i=i<relatedExpert.size()?i:relatedExpert.size();
        for(int k=0;k<i;k++){
            names.add(relatedExpert.get(k).getFullname());
        }
        return names;
    }

    public static List<String> getKArticle(int k,ExpertMongo expert) {
        List<String>res=new ArrayList<>();
        for(PmidMongo article:expert.getPmids()){
            res.add(article.getTitle());
            k--;
            if(k==0)break;
        }
        return res;
    }

    public static List<List<String>> getEvolution(ExpertMongo expert) {
       List<KeywordCount> keywords=expert.getAll_keywords();
       Collections.sort(keywords, new Comparator<KeywordCount>() {

            @Override
            public int compare(KeywordCount keywordCount, KeywordCount t1) {
                return keywordCount.getYear()-t1.getYear();
            }
        });
       List<String>keys=getTopKKeywords(10,expert);
       HashMap<String,HashMap<String,Integer>>countByYear=new HashMap<>();
       for(KeywordCount count:keywords){
           HashMap<String,Integer>yearCount=new HashMap<>();
           for(String key:count.getKeywords()){
               if(keys.contains(key)){
                   if(!yearCount.containsKey(key))yearCount.put(key,0);
                   yearCount.put(key,yearCount.get(key)+1);
               }
           }
           countByYear.put(String.valueOf(count.getYear()),yearCount);
       }
       List<List<String>>res=new ArrayList<>();
       for(String year:countByYear.keySet()){

           for(String k:countByYear.get(year).keySet()){
               res.add(new ArrayList(){{
                   add(year+"/01/01");
                   add(countByYear.get(year).get(k));
                   add(k);
               }});
           }
       }
       return res;
    }

    public static ExpertDistributionData getDistribution(List<ExpertMongo> relatedExperts) {
        ExpertDistributionData data=new ExpertDistributionData();
        data.setName("expert");
        List<ExpertDistributionData>details=new ArrayList<>();
        int k=10;
        for(ExpertMongo expertMongo:relatedExperts){
            ExpertDistributionData expert=new ExpertDistributionData();
            expert.setName(expertMongo.getFullname());
            List<ExpertDistributionData>children=new ArrayList<>();
            for(String keyword:getTopKKeywords(5,expertMongo)){
                children.add(new ExpertDistributionData(keyword,new ArrayList<ExpertDistributionData>()));
            }
            expert.setChildren(children);
            details.add(expert);
            k--;
            if(k==0)break;

        }
        data.setChildren(details);
        return data;
    }

    public static HashMap<String, Integer> getKeywordsDistribution(int i, ExpertMongo expert) {
        HashMap<String,Integer>keywordCount=new HashMap<>();
        for(KeywordCount keywords:expert.getAll_keywords()){
            for(String key:keywords.getKeywords()){
                if(!keywordCount.containsKey(key))keywordCount.put(key,0);
                keywordCount.put(key,keywordCount.get(key)+1);
            }
        }
        Map<String, Integer> sortedMap=
                keywordCount.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.<String, Integer>comparingByValue())).
                        collect(Collectors.toMap(e->e.getKey(), e->e.getValue(),(v1, v2)->v2,LinkedHashMap::new));
        HashMap<String,Integer>res=new HashMap<>();
        for(String key:sortedMap.keySet()){
            res.put(key,sortedMap.get(key));
            i--;
            if(i==0)break;
        }
        return res;
    }

    public static HashMap<String, Integer> getPublicationDistribution(ExpertMongo expert) {
        HashMap<String,Integer>res=new HashMap<>();
        for(PmidMongo pmid:expert.getPmids()){
            if(!res.containsKey(pmid.getPubyear()))res.put(pmid.getPubyear(),0);
            res.put(pmid.getPubyear(),res.get(pmid.getPubyear())+1);
        }
        return res;
    }
}

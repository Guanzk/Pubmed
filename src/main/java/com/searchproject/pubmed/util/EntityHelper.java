package com.searchproject.pubmed.util;

import com.searchproject.pubmed.Bean.EntityMongo;
import com.searchproject.pubmed.Bean.auxiliary.*;
import com.searchproject.pubmed.Bean.json.Node;
import org.springframework.data.redis.listener.Topic;

import java.util.*;

public class EntityHelper {
    public static String getLatestYearCount(EntityMongo entity) {
        if(entity.getPaper_count_by_year().getYears().size()<=0)return "";
        YearCount latest=entity.getPaper_count_by_year().getYears().get(0);
        for(YearCount year:entity.getPaper_count_by_year().getYears()){
            if(year.getYear()>latest.getYear())latest=year;
        }
        return String.valueOf(latest.getCount());
    }

    public static String getHostestYear(EntityMongo entity) {
        if(entity.getPaper_count_by_year().getYears().size()<=0)return "";
        YearCount latest=entity.getPaper_count_by_year().getYears().get(0);
        for(YearCount year:entity.getPaper_count_by_year().getYears()){
            if(year.getCount()>latest.getCount())latest=year;
        }
        return String.valueOf(latest.getYear());
    }

    public static List<String> getTopKTopic(int k,EntityMongo entity) {
        List<String>list=new ArrayList<>();
        List<TopicDistribution>topics= entity.getTopic_distribution();
        Collections.sort(topics, new Comparator<TopicDistribution>() {
            @Override
            public int compare(TopicDistribution topicDistribution, TopicDistribution t1) {
                return t1.getCount()-topicDistribution.getCount();
            }
        });
        for(TopicDistribution topic:topics){
            list.add(topic.getTopic());
            k--;
            if(k==0)break;
        }
        return list;
    }

    public static List<String> getTopKCountry(int i, EntityMongo entity) {
        List<String>list=new ArrayList<>();
        List<Node> countries=entity.getCountry_distribution();
        Collections.sort(countries, new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return t1.getValue()-node.getValue();
            }
        });
        for(Node node:countries){
            list.add(node.getName());
            i--;
            if(i==0)break;
        }
        return list;
    }

    public static List<String> getTopKOrganization(int i, EntityMongo entity) {
        List<String>list=new ArrayList<>();
        List<RelatedAffiliation> affiliations=entity.getRelated_affiliations();
        Collections.sort(affiliations, new Comparator<RelatedAffiliation>() {
            @Override
            public int compare(RelatedAffiliation relatedAffiliation, RelatedAffiliation t1) {
                return (int) (t1.getScore()-relatedAffiliation.getScore());
            }
        });
        for(RelatedAffiliation affiliation:affiliations){
            list.add(affiliation.getAffiliation());
            i--;
            if(i==0)break;
        }
        return list;
    }

    public static List<String> getTopKExpert(int i, EntityMongo entity) {
        List<String>list=new ArrayList<>();
        List<EntityNode>experts=entity.getExpert_relation().getDetail();
        Collections.sort(experts, new Comparator<EntityNode>() {
            @Override
            public int compare(EntityNode entityNode, EntityNode t1) {
                return t1.getInfluence()-entityNode.getInfluence();
            }
        });
        for(EntityNode node:experts){
            list.add(node.getName());
            i--;
            if(i==0)break;
        }
        return list;
    }

    public static HashMap<String, Integer> getTopKTopicMap(int i, EntityMongo entity) {
        List<TopicDistribution>topics= entity.getTopic_distribution();
        Collections.sort(topics, new Comparator<TopicDistribution>() {
            @Override
            public int compare(TopicDistribution topicDistribution, TopicDistribution t1) {
                return t1.getCount()-topicDistribution.getCount();
            }
        });
        HashMap<String,Integer>map=new HashMap<>();
        for(TopicDistribution topic:topics){
           map.put(topic.getTopic(),topic.getCount());
           i--;
           if(i==0)break;
        }
       return map;

    }

    public static HashMap<String, Integer> getTopKCountryMap(int i, EntityMongo entity) {
        List<Node> countries=entity.getCountry_distribution();
        Collections.sort(countries, new Comparator<Node>() {
            @Override
            public int compare(Node node, Node t1) {
                return t1.getValue()-node.getValue();
            }
        });
        HashMap<String ,Integer>map=new HashMap<>();
        for(Node node:countries){
            map.put(node.getName(),node.getValue());
            i--;
            if(i==0)break;
        }
        return map;
    }

    public static HashMap<String, Double> getTopKOrganizationMap(int i, EntityMongo entity) {
        List<RelatedAffiliation> affiliations=entity.getRelated_affiliations();
        Collections.sort(affiliations, new Comparator<RelatedAffiliation>() {
            @Override
            public int compare(RelatedAffiliation relatedAffiliation, RelatedAffiliation t1) {
                return (int) (t1.getScore()-relatedAffiliation.getScore());
            }
        });
        HashMap<String, Double>map=new HashMap<>();
        for(RelatedAffiliation affiliation:affiliations){
            map.put(affiliation.getAffiliation(),affiliation.getScore());
            i--;
            if(i==0)break;
        }
        return map;
    }

    public static List<Node> getNode(EntityMongo entity) {
        List<Node>list=new ArrayList<Node>(){{
            for(EntityNode entityNode:entity.getExpert_relation().getDetail()){
                Node node=new Node();
                node.setName(entityNode.getName());
                node.setValue(entityNode.getInfluence());
                add(node);
            }

        }};
        return list;
    }
}

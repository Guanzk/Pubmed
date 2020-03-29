package com.searchproject.pubmed.dao;

import com.google.protobuf.ProtocolStringList;
import com.searchproject.pubmed.Bean.EntityMongo;
import com.searchproject.pubmed.Bean.ExpertMongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Component
public class MongoDao {
    @Autowired
    MongoArticleRepository mongoArticleRepository;
    @Autowired
    MongoEntityRepository mongoEntityRepository;
    @Autowired
    MongoExpertRepository mongoExpertRepository;
    public ExpertMongo getExpert(long aid){
        return mongoExpertRepository.findByAid(aid);
    }
    public List<ExpertMongo>getExperts(List<String>aids){
        List<Long>aidL=new ArrayList<Long>(){{
            for(String aid:aids){
                add(Long.parseLong(aid));
            }
        }};
//        return mongoExpertRepository.findAllByAidIn(aids.stream().map(Long::parseLong).collect(Collectors.toList()));
        List<ExpertMongo>res=mongoExpertRepository.findAllByAidIn(aidL);


        return res;
    }
    public EntityMongo getEntity(String name){
        return mongoEntityRepository.findByName(name);
    }

    public List<EntityMongo> getEntitys(List<String> entitys) {
        return mongoEntityRepository.findByNameIn(entitys);
    }
}

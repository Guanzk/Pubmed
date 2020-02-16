package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.ExpertMongo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoExpertRepository extends MongoRepository<ExpertMongo, ObjectId> {
    ExpertMongo findByAid(long aid);
    List<ExpertMongo>findAllByAidIn(List<Long>aids);
}

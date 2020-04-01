package com.searchproject.pubmed.test;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoUserRepository  extends MongoRepository<ArticleMongo, ObjectId> {
    ArticleMongo findByPmid(Integer pmid);

}
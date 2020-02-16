package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.ArticleMongo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoArticleRepository extends MongoRepository<ArticleMongo, ObjectId> {
    ArticleMongo findByPmid(Integer pmid);

}

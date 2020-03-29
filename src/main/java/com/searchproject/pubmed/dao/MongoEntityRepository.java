package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.EntityMongo;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MongoEntityRepository extends MongoRepository<EntityMongo, ObjectId> {
    EntityMongo findByName(String name);
    List<EntityMongo> findByNameIn(List<String>name);
}

package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.Good;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsRepository extends MongoRepository<Good, ObjectId> {
    Good findByName(String name);
    List<Good> findByNameIn(List<String>names);
}

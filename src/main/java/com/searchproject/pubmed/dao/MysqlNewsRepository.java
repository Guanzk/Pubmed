package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MysqlNewsRepository extends JpaRepository<News, Integer> {
    List<News> findAllByIdIn(List<Integer> newsIdList);
    News findById(int newsId);
    News findByTitle(String newsTitle);
}
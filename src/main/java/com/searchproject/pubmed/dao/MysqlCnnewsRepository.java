package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.CnNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MysqlCnnewsRepository extends JpaRepository<CnNews, String> {
    List<CnNews> findAllByCnnewsidIn(List<String> cnnewsIdList);
    CnNews findByCnnewsid(String cnnewsId);
    CnNews findByTitle(String cnnewsTitle);
}
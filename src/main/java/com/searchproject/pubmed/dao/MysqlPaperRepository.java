package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MysqlPaperRepository extends JpaRepository<Paper, String> {
    List<Paper> findAllByPaperlongidIn(List<String> paperIdList);
    Paper findByPaperlongid(String paperId);
    Paper findByPapertitle(String paperTitle);
}
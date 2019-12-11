package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleSimpleDao extends JpaRepository<Article,String> {
    List<Article> findAllByPMIDIn(List<String>pmids);
}

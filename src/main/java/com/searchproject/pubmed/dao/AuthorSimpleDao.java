package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.AuthorSimple;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorSimpleDao extends JpaRepository<AuthorSimple,String> {
    List<AuthorSimple>findAllByPmidIn(List<String> pmids);
    List<AuthorSimple>findAllByAidIn(List<String>aids);
}

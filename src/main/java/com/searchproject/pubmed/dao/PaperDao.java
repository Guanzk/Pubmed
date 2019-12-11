package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.Paper;
import com.searchproject.pubmed.Bean.PaperId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaperDao extends JpaRepository<Paper, PaperId> {
    List<Paper>findAllByAidIn(List<String> aidList);
}

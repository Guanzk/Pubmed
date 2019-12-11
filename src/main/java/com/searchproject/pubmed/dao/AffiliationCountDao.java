package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.AffiliationCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AffiliationCountDao extends JpaRepository<AffiliationCount,String> {
    List<AffiliationCount> findAllByAffiliationIn(List<String>affiliations);
}

package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.Patent_new;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MysqlPatentRepository extends JpaRepository<Patent_new, String> {
    List<Patent_new> findAllByPatentidIn(List<String> patentIdList);
    Patent_new findByPatentid(String patentId);
    Patent_new findByPatenttitle(String patentTitle);
}
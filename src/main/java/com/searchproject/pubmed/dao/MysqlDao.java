package com.searchproject.pubmed.dao;

import com.searchproject.pubmed.Bean.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MysqlDao {
    @Autowired
    MysqlCnnewsRepository mysqlCnnewsRepository;
    @Autowired
    MysqlNewsRepository mysqlNewsRepository;
    @Autowired
    MysqlPaperRepository mysqlPaperRepository;
    @Autowired
    MysqlPatentRepository mysqlPatentRepository;
    public Paper getPaper(String paper_long_id) {return mysqlPaperRepository.findByPaperlongid(paper_long_id);}
    public Patent_new getPatent(String patent_id) {return mysqlPatentRepository.findByPatentid(patent_id);}
    public CnNews getCnnew(String cnnews_id) {return mysqlCnnewsRepository.findByCnnewsid(cnnews_id);}
    public News getNew(int news_id) {return mysqlNewsRepository.findById(news_id);}

    public Paper getPaperWithTitle(String paper_title) {return mysqlPaperRepository.findByPapertitle(paper_title);}
    public Patent_new getPatentWithTitle(String patent_title) {return mysqlPatentRepository.findByPatenttitle(patent_title);}
    public CnNews getCnnewWithTitle(String cnnews_title) {return mysqlCnnewsRepository.findByTitle(cnnews_title);}
    public News getNewsWithTitle(String news_title) {return mysqlNewsRepository.findByTitle(news_title);}


    public List<Paper> getPapers(List<String> paper_long_ids) {
        return mysqlPaperRepository.findAllByPaperlongidIn(paper_long_ids);
    }
    public List<Patent_new> getPatents(List<String> patent_ids) {
        return mysqlPatentRepository.findAllByPatentidIn(patent_ids);
    }
    public List<CnNews> getCnnews(List<String> cnnews_ids) {
        return mysqlCnnewsRepository.findAllByCnnewsidIn(cnnews_ids);
    }
    public List<News> getNews(List<Integer> news_ids) {
        return mysqlNewsRepository.findAllByIdIn(news_ids);
    }
}

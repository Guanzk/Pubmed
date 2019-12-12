package com.searchproject.pubmed.util;



import com.searchproject.pubmed.Bean.Article;
import com.searchproject.pubmed.Bean.Author;
import com.searchproject.pubmed.Bean.AuthorInformation;
import com.searchproject.pubmed.Bean.MedEntity;
import com.searchproject.pubmed.dao.ArticleUtil;

import com.searchproject.pubmed.dao.AuthorUtil;
import com.searchproject.pubmed.dao.MedEntityDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class QueryResult {
@Autowired
AuthorUtil authorUtil;
@Autowired
ArticleUtil articleUtil;
    public  Author processAuhtor(String aid) {

        log.info("begin to process auhtor");
        Author author = new Author();
        AuthorInformation authorInformation = authorUtil.getAuthorInformation(aid);
        List<String> pmids = AuthorUtil.getPmids(aid);
        HashMap<String, List<Article>> publicationsByYear = authorUtil.getPublicationsByYear(pmids);
        HashMap<String, HashMap<String, Integer>> keywordsByYear = AuthorUtil.getKeywordsByYear(publicationsByYear);
        List<Author> otherExperts = AuthorUtil.getOtherExperts(authorInformation.getAffiliation(), aid);
        author = new Author() {{
            setFullName(AuthorUtil.getFullNameByAid(aid));
            setCountry(authorInformation.getCountry());
            setAffiliation(authorInformation.getAffiliation());
            setDepartment(authorInformation.getDepartment());
            setPmids(pmids);
            setPublicationsByYear(publicationsByYear);
            setKeywordsByYear(keywordsByYear);
            setOtherExpert(otherExperts);
            setKeywordCount(AuthorUtil.getAuthorKeywords(aid));


        }};
        return author;
    }

    public  MedEntity processMedEntity(String query) {
        long start = System.currentTimeMillis();
        MedEntity entity = null;
        Set<String> type = MedEntityDao.getEntityName(query);
        List<String> pmids = MedEntityDao.getPmids(query);
        log.info("pmids:"+pmids);
        HashMap<String, List<Article>> publicationsByYear = authorUtil.getPublicationsByYear(new LinkedList<String>() {{
            addAll(pmids);
        }});
        HashMap<String, HashMap<String, Integer>> keywordsByYear = AuthorUtil.getKeywordsByYear(publicationsByYear);
        HashMap<String, List<String>> pmidByAffiliation = ArticleUtil.getAffiliations(pmids);
        HashMap<String, Integer> affiliationTotalPapers = articleUtil.getAffiliationTotalPapers(pmidByAffiliation.keySet());
        List<AuthorInformation> relatedAuthors = authorUtil.getEntityRelatedAuthors(pmids);//关于该基因的作者
        HashMap<String, List<String>> pmidByAid = ArticleUtil.getPmidByAid(relatedAuthors);
        HashMap<String, Integer> pmidCitation = ArticleUtil.getPmidCitation(pmids);
        long end = System.currentTimeMillis();
        log.debug("完成医药数据获取，用时：" + (end - start));
        entity = new MedEntity() {{
            setName(query);
            setTypes(type);
            setPmids(pmids);
            setPublicationsByYear(publicationsByYear);
            setKeywordsByYear(keywordsByYear);
            setPmidByAffiliation(pmidByAffiliation);
            setAuthors(relatedAuthors);//set aid and keywords
            setAuthorLink(new HashMap<String, List<String>>());//TODO 等待数据处理
            setPmidByAid(pmidByAid);
            setPmidCitation(pmidCitation);
            setAffiliationTotalPapers(affiliationTotalPapers);
        }};


        return entity;
    }
}

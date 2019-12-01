package Server;

import Bean.Article;
import Bean.Author;
import Bean.AuthorInformation;
import Bean.MedEntity;
import DAO.*;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
public class QueryResult {

    public static Author processAuhtor(String aid){
        log.info("begin to process auhtor");
        Author author=new Author();
        AuthorInformation authorInformation= AuthorUtil.getAuthorInformation(aid);
        List<String>pmids=AuthorUtil.getPmids(aid);
        HashMap<String,List<Article>> publicationsByYear=AuthorUtil.getPublicationsByYear(pmids);
        HashMap<String,HashMap<String,Integer>>keywordsByYear=AuthorUtil.getKeywordsByYear(publicationsByYear);
        List<Author>otherExperts= AuthorUtil.getOtherExperts(authorInformation.getAffiliation(),aid);
        author=new Author(){{
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

    public static MedEntity processMedEntity(String query) {
        long start=System.currentTimeMillis();
        MedEntity entity=null;
        Set<String> type= MedEntityDao.getEntityName(query);
        List<String>pmids=MedEntityDao.getPmids(query);
        HashMap<String,List<Article>> publicationsByYear=AuthorUtil.getPublicationsByYear(new LinkedList<String>(){{
            addAll(pmids);
        }});
        HashMap<String,HashMap<String,Integer>>keywordsByYear=AuthorUtil.getKeywordsByYear(publicationsByYear);
        HashMap<String,List<String>>pmidByAffiliation= ArticleDao.getAffiliations(pmids);
        HashMap<String,Integer>affiliationTotalPapers=ArticleDao.getAffiliationTotalPapers(pmidByAffiliation.keySet());
        List<AuthorInformation>relatedAuthors= AuthorDao.getEntityRelatedAuthors(pmids);//关于该基因的作者
        HashMap<String,List<String>>pmidByAid=ArticleDao.getPmidByAid(relatedAuthors);
        HashMap<String,Integer>pmidCitation=ArticleDao.getPmidCitation(pmids);
        long end=System.currentTimeMillis();
        log.debug("完成医药数据获取，用时："+(end-start));
        entity=new MedEntity(){{
            setName(query);
            setTypes(type);
            setPmids(pmids);
            setPublicationsByYear(publicationsByYear);
            setKeywordsByYear(keywordsByYear);
            setPmidByAffiliation(pmidByAffiliation);
            setAuthors(relatedAuthors);//set aid and keywords
            setAuthorLink(new HashMap<String,List<String>>());//TODO 等待数据处理
            setPmidByAid(pmidByAid);
            setPmidCitation(pmidCitation);
            setAffiliationTotalPapers(affiliationTotalPapers);
        }};


        return entity;
    }
}

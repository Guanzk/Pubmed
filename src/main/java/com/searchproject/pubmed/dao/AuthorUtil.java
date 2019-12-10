package com.searchproject.pubmed.dao;


import com.searchproject.pubmed.Bean.Article;
import com.searchproject.pubmed.Bean.Author;
import com.searchproject.pubmed.Bean.AuthorInformation;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Tuple;

import java.util.*;

@Slf4j
public class AuthorUtil {
    private static ReadDataFromRedis redis = new ReadDataFromRedis();

    public static List<String> getPublicationTitle(HashMap<String, List<Article>> publicationsByYear) {
        List<String> res = new LinkedList<>();
        for (String year : publicationsByYear.keySet()) {
            for (Article article : publicationsByYear.get(year)) {
                res.add(article.getArticleTitle());
            }
        }
        return res;
    }

    public static AuthorInformation getAuthorInformation(String aid) {
        AuthorInformation authorInformation = ReadAuthorFromMySQL.getAuthorFromAid(aid);
        return authorInformation;
    }

    public static HashMap<String, List<Article>> getPublicationsByYear(List<String> pmids) {
        HashMap<String, List<Article>> publicationsByYear = new HashMap<>();
        List<Article> articles = ReadArticleFromMySQL.lookUp(pmids);
        log.debug("获取文章相关数据成功");
        for (Article article : articles) {
            if (!publicationsByYear.containsKey(article.getPubyear())) {
                ArrayList<Article> a = new ArrayList<>();
                a.add(article);
                publicationsByYear.put(article.getPubyear(), a);
            } else {
                publicationsByYear.get(article.getPubyear()).add(article);
            }
        }
        return publicationsByYear;
    }

    public static List<String> getPmids(String aid) {
        List<String> pmids = new ArrayList<>();
        pmids.addAll(ReadDataFromRedis.getPMIDfromaid(redis, aid));
        return pmids;
    }

    public static HashMap<String, HashMap<String, Integer>> getKeywordsByYear(HashMap<String, List<Article>> publicationsByYear) {
        HashMap<String, HashMap<String, Integer>> keywordsByYear = new HashMap<>();
        for (String year : publicationsByYear.keySet()) {

            if (!keywordsByYear.containsKey(year)) {
                keywordsByYear.put(year, new HashMap<String, Integer>());
            }
            HashMap<String, Integer> keys = keywordsByYear.get(year);
            for (Article article : publicationsByYear.get(year)) {
                Set<String> keywords = ReadDataFromRedis.getKeywordsFromPMID(redis, String.valueOf(article.getPMID()));
                for (String keyword : keywords) {
                    if (!keys.containsKey(keyword)) {
                        keys.put(keyword, 1);
                    } else {
                        keys.put(keyword, keys.get(keyword) + 1);
                    }
                }
            }
            keywordsByYear.put(year, keys);
        }
        return keywordsByYear;

    }

    public static String getFullNameByAid(String aid) {
        return ReadDataFromRedis.getFullNameAuthorfromaid_Pool(redis, aid);
    }

    //aid:自己的aid
    public static List<Author> getOtherExperts(String affiliation, String aid) {
        log.info("get other experts");
        Set<String> aids = ReadDataFromRedis.getAidfromAffiliation(redis, affiliation);
        aids.remove(aid);
//        HashMap<String,AuthorInformation> authorInformations=ReadAuthorFromMySQL.getAuthorsFromAid(new ArrayList<String>(aids));
        List<Author> tempAuthors = new ArrayList<>();
        for (String id : aids) {
            Author author = new Author() {{
                setPmids(AuthorUtil.getPmids(id));
                setKeywordCount(getAuthorKeywords(id));
                setFullName(ReadDataFromRedis.getFullNameAuthorfromaid_Pool(redis, id));
            }};
            tempAuthors.add(author);
        }
        Collections.sort(tempAuthors, (o1, o2) -> o2.getPmids().size() - o1.getPmids().size());
        List<Author> authors = new ArrayList<>();
        //获得前5个
        for (int i = 0; i < 5 && i < tempAuthors.size(); i++) {
            authors.add(tempAuthors.get(i));
        }
        log.info("get other expert size:" + aids.size());
        return authors;
    }

    public static HashMap<String, Integer> getAuthorKeywords(String aid) {
        Set<Tuple> top5Tuple = ReadDataFromRedis.getKeywordsFromAid(redis, aid);
        HashMap<String, Integer> keywords = new HashMap<>();
        for (Tuple tuple : top5Tuple) {
            keywords.put(tuple.getElement(), (int) tuple.getScore());
        }

        return keywords;

    }

    public static List<String> getTopKeywords(HashMap<String, Integer> keywordCount, int i) {
        List<String> res = new LinkedList<>();
        int size = keywordCount.size();
        List<Map.Entry<String, Integer>> mapList = new ArrayList<>(keywordCount.entrySet());
        Collections.sort(mapList, (o1, o2) -> o2.getValue() - o1.getValue());
        if (size <= i) {
            for (int k = 0; k < size; k++) {
                res.add(mapList.get(k).getKey());
            }
        } else {

            for (int k = 0; k < i; k++) {
                res.add(mapList.get(k).getKey());
            }
        }
        return res;
    }
}

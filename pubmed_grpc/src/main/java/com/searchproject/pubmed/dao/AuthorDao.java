package com.searchproject.pubmed.dao;


import com.searchproject.pubmed.Bean.AuthorInformation;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class AuthorDao {
    private static ReadDataFromRedis redis = new ReadDataFromRedis();

    public static List<AuthorInformation> getEntityRelatedAuthors(List<String> pmids) {
        List<AuthorInformation> res = ReadAuthorFromMySQL.getAuthorsFromPmids(pmids);
        long start = System.currentTimeMillis();
        res = ReadDataFromRedis.getKeywordsFromAids(redis, res);

        long end = System.currentTimeMillis();
        log.debug("获取医药实体相关作者用时：" + (end - start));
        return res;
    }
}

package com.searchproject.pubmed.dao;



import com.searchproject.pubmed.Bean.Article;
import com.searchproject.pubmed.config.MySQLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadArticleFromMySQL extends ReadDataFromMySQL {

    private static final Logger logger = LoggerFactory.getLogger(ReadArticleFromMySQL.class);

    public static ArrayList<Article> lookUp(List<String> pmidList) {
        ArrayList<Article> articlesSimples = new ArrayList<Article>();
        logger.info("pmids Length:" + pmidList.size());
        long start = System.currentTimeMillis();
        try {

            conn = SQLPool.getConnection();
            stmt = conn.createStatement();
            String useDB = MySQLConfig.get("DATABASE");
            stmt.executeUpdate("use " + useDB);
            logger.info("------Connected with DATABASE!------");

            for (String str : pmidList) {
                //通过PMID列表来逐个搜索PMID所对应的文章标题、出版年份及参与作者数量
                String selectarticles = "select * from " + MySQLConfig.get("ARTICLES_TABLE_NAME") + " where " + MySQLConfig.get("article_PMID") + "=" + Integer.parseInt(str);
                rs = stmt.executeQuery(selectarticles);

                while (rs.next()) {
                    articlesSimples.add(getArticlesSimpleFromMySQL(rs));

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLPool.release(conn, stmt, rs);
        }
        long end = System.currentTimeMillis();
        logger.info("------Build ArrayList abt. articles successfully!------");
        logger.info("Final Length:" + articlesSimples.size() + "用时:" + (end - start));
        return articlesSimples;
    }

    public static Map<String, Integer> getAffiliationsTotalPapers(List<String> affiliations) {
        long start = System.currentTimeMillis();
        logger.debug("affiliations size:" + affiliations.size());
        Map<String, Integer> res = new HashMap<>();
        try {

            conn = SQLPool.getConnection();
            stmt = conn.createStatement();
            String useDB = MySQLConfig.get("DATABASE");
            stmt.executeUpdate("use " + useDB);
            logger.info("------Connected with DATABASE!------");

            for (String affiliation : affiliations) {
                String sql = "select total_count from Affiliation_PubyearCounter where Affiliation=?";
//                logger.debug(sql+affiliation);
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, affiliation);
                rs = ps.executeQuery();
                if (rs.next()) {
                    res.put(affiliation, rs.getInt(1));
                } else {
                    logger.info("找不到" + affiliation + "出版数");

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLPool.release(conn, stmt, rs);

        }
        long end = System.currentTimeMillis();
        logger.info("检索affiliation出版数用时：" + (end - start));
        return res;
    }

    private static Article getArticlesSimpleFromMySQL(ResultSet rs) throws SQLException {
        Article n = new Article();
        n.setPMID(rs.getInt(MySQLConfig.get("article_PMID")));
        n.setArticleTitle(rs.getString(MySQLConfig.get("article_title")));
        n.setPubyear(rs.getString(MySQLConfig.get("article_pubyear")));
        n.setAuthorNum(rs.getInt(MySQLConfig.get("article_author_num")));
        return n;
    }
}
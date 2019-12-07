package com.searchproject.pubmed.dao;





import com.searchproject.pubmed.Bean.AuthorInformation;
import com.searchproject.pubmed.config.MySQLConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReadAuthorFromMySQL extends ReadDataFromMySQL {

    private static final Logger logger = LoggerFactory.getLogger(ReadAuthorFromMySQL.class);

    public static AuthorInformation findAuthorByAids(List<String> aidList) {

        //初始化Hash表:consisted of aid & the list of the Affiliation

        logger.info("开始查找一个有用的作者，aid size:" + aidList.size());
        try {

            conn = SQLPool.getConnection();
            stmt = conn.createStatement();
            String useDB = MySQLConfig.get("DATABASE");
            stmt.executeUpdate("use " + useDB);
            logger.info("------Connected with DATABASE!------");

            for (String str : aidList) {
                //通过Redis所返回的aid来查询所对应的Affliation，从而写入到结果集中


                String selectAffiliation = "select * from " + MySQLConfig.get("AFFILIATION_TABLE_NAME") + " where " + MySQLConfig.get("affiliation_aid") + "=\"" + str + "\"";
                rs = stmt.executeQuery(selectAffiliation);
                if (rs.next()) {
                    return getAffiliationFromMySQL(rs);
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLPool.release(conn, stmt, rs);
        }

        logger.info("找不到有用作者");
        return null;
    }

    public static HashMap<String, AuthorInformation> getAuthorsFromAid(List<String> aidList) {

        //初始化Hash表:consisted of aid & the list of the Affiliation
        HashMap<String, AuthorInformation> hMap = new HashMap<>();

        logger.info("Initial Length:" + hMap.size());
        try {

            conn = SQLPool.getConnection();
            stmt = conn.createStatement();
            String useDB = MySQLConfig.get("DATABASE");
            stmt.executeUpdate("use " + useDB);
            logger.info("------Connected with DATABASE!------");

            for (String str : aidList) {
                //通过Redis所返回的aid来查询所对应的Affliation，从而写入到结果集中
                String selectAffiliation = "select * from " + MySQLConfig.get("AFFILIATION_TABLE_NAME") + " where " + MySQLConfig.get("affiliation_aid") + "=\"" + str + "\"";
                rs = stmt.executeQuery(selectAffiliation);
                rs.next();//会返回很多个，就拿一个
                hMap.put(str, getAffiliationFromMySQL(rs));

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLPool.release(conn, stmt, rs);
        }

        logger.info("------Build HashMap based on aid & Affiliations successfully!------");
        logger.info("Final Length:" + hMap.size());
        return hMap;
    }

    public static AuthorInformation getAuthorFromAid(String aid) {

        //初始化Hash表:consisted of aid & the list of the Affiliation
        AuthorInformation authorInformation = new AuthorInformation();

        logger.info("get a author");
        try {

            conn = SQLPool.getConnection();
            stmt = conn.createStatement();
            String useDB = MySQLConfig.get("DATABASE");
            stmt.executeUpdate("use " + useDB);
            logger.info("------Connected with DATABASE!------");
            String selectAffiliation = "select * from " + MySQLConfig.get("AFFILIATION_TABLE_NAME") + " where " + MySQLConfig.get("affiliation_aid") + "=\"" + aid + "\"";
            rs = stmt.executeQuery(selectAffiliation);
            //会返回很多个，就拿一个

            if (rs.next()) {
                authorInformation = getAffiliationFromMySQL(rs);
            } else {
                logger.info("找不到作者");
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLPool.release(conn, stmt, rs);
        }

        logger.info("------get author successfully!------");

        return authorInformation;
    }

    public static ArrayList<AuthorInformation> findAffiliationByAid(String aid) {

        //初始化Hash表:consisted of aid & the list of the Affiliation
        ArrayList<AuthorInformation> result = new ArrayList<>();


        try {
            int index = 0;
            conn = SQLPool.getConnection();
            stmt = conn.createStatement();
            String useDB = MySQLConfig.get("DATABASE");
            stmt.executeUpdate("use " + useDB);
            logger.info("------Connected with DATABASE!------");


            //通过Redis所返回的aid来查询所对应的Affliation，从而写入到结果集中

            String selectAffiliation = "select Affiliation from " + MySQLConfig.get("AFFILIATION_TABLE_NAME") + " where " + MySQLConfig.get("affiliation_aid") + "=\"" + aid + "\"";
            rs = stmt.executeQuery(selectAffiliation);
            while (rs.next()) {
                result.add(getAffiliationFromMySQL(rs));
                index++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SQLPool.release(conn, stmt, rs);
        }

        logger.info("------Build HashMap based on aid & Affiliations successfully!------");
        logger.info("Final Length:" + result.size());
        return result;
    }

    public static HashMap<String, List<String>> getSpeciesFromPmids(List<String> pmids) {
        HashMap<String, List<String>> species = new HashMap<>();
        species.put("gene", new ArrayList<String>());
        species.put("desease", new ArrayList<String>());
        species.put("species", new ArrayList<String>());
        species.put("mutation", new ArrayList<String>());
        species.put("drug", new ArrayList<String>());
        conn = SQLPool.getConnection();
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("use " + MySQLConfig.get("DATABASE"));
            logger.info("------Connected with DATABASE!------");
            StringBuilder query = new StringBuilder("select distinct entity,type from PubmedNer where ");
            for (String pmid : pmids) {
                query.append("PMID=" + pmid + " or ");
            }
            query.delete(query.length() - 3, query.length());
            rs = stmt.executeQuery(query.toString());
            while (rs.next()) {
                System.out.println(rs.getString("type"));
                species.get(rs.getString("type")).add(rs.getString("entity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return species;

    }

    private static AuthorInformation getAffiliationFromMySQL(ResultSet rs) throws SQLException {
        AuthorInformation n = new AuthorInformation();
        n.setAid(rs.getString(MySQLConfig.get("affiliation_aid")));
        n.setPMID(rs.getInt(MySQLConfig.get("affiliation_PMID")));
        n.setAffiliation(rs.getString(MySQLConfig.get("affiliation_Affiliation")));
        n.setEmail(rs.getString(MySQLConfig.get("affiliation_Email")));
        n.setZipcode(rs.getString(MySQLConfig.get("affiliation_Zipcode")));
        n.setCountry(rs.getString("Country"));
        n.setDepartment(rs.getString("Department"));

        return n;
    }

    public static List<AuthorInformation> getAuthorsFromPmids(List<String> pmids) {
        List<AuthorInformation> res = new LinkedList<>();
        if (pmids.size() == 0) {
            return res;
        }
        logger.debug("getAuthorsFromPmids pmid size:" + pmids.size());
        long start = System.currentTimeMillis();
        try {
            conn = SQLPool.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate("use " + MySQLConfig.get("DATABASE"));
            logger.info("------Connected with DATABASE!------");
            for (String pmid : pmids) {
                String query = "select aid,FullName from AuthorList where PMID=" + pmid;
                rs = stmt.executeQuery(query);
                if (rs.next()) {
                    res.add(new AuthorInformation() {{
                        setAid(String.valueOf(rs.getInt(1)));
                        setFullName(rs.getString(2));
                    }});
                } else {
                    logger.info("找不到" + pmid + "相关aid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("getAuthorsFromPmids 用时：" + (end - start));
        logger.debug("result size:" + res.size());
        return res;

    }

    public static void main(String[] args) {

//        ReadAuthorFromMySQL r=new ReadAuthorFromMySQL();
//        try {
//            MySQLConfig.load("mysql.conf");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        HashMap<String,List<String>>spes=r.getSpeciesFromPmids(new ArrayList<String>(){
//            {add("5849239");
//            add("6900147");
//        }
//        });
//        System.out.println(spes);
    }


}
package com.searchproject.pubmed.Bean;

/**
 * 数据源 Affiliaition
 * 数据格式
 * 作者专属aid ； 作者著作PMID ； 作者相关信息Affiliation、Email、Zipcode
 */

import java.util.List;

/**
 * author: @DrRic
 */

public class AuthorInformation {

    //从Redis获得aid查询
    private String aid = "";

    //根据aid获得对应的PMID用以查询后续的相关操作
    private String PMID;
    private String Affiliation = "";
    private String Email = "";
    private String Zipcode = "";
    private String department;
    private String country;
    private String fullName = "";
    private List<String> keywords;

    public AuthorInformation() {
    }

    public AuthorInformation(String aid, String pmid, String affiliation, String email, String zipcode) {
        this.aid = aid;
        PMID = pmid;
        Affiliation = affiliation;
        Email = email;
        Zipcode = zipcode;
    }

    //返回所获得的结果集，为单行数据
    @Override
    public String toString() {
        String ans = "";
        ans += "aid:" + aid + " PMID:" + PMID + " Affiliation:" + Affiliation + " Email:" + Email + " Zipcode:" + Zipcode;
        return ans;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getAid() {
        return this.aid;
    }

    public void setPMID(String PMID) {
        this.PMID = PMID;
    }

    public String getPMID() {
        return this.PMID;
    }

    public void setAffiliation(String affiliation) {
        Affiliation = affiliation;
    }

    public String getAffiliation() {
        return Affiliation;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getEmail() {
        return Email;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}

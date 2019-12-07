package com.searchproject.pubmed.Bean;

import java.util.*;

public class MedEntity {
    private String name;
    private Set<String> types;
    private String description = "";
    private List<String> pmids;
    private HashMap<String, List<Article>> publicationsByYear;
    private HashMap<String, HashMap<String, Integer>> keywordsByYear;
    private HashMap<String, List<String>> pmidByAffiliation;
    private HashMap<String, Integer> affiliationTotalPapers;
    private HashMap<String, Integer> affiliationEntityPapers;
    private List<AuthorInformation> authors;
    private HashMap<String, List<String>> pmidByAid;
    private HashMap<String, Integer> pmidCitation;
    private HashMap<String, List<String>> authorLink;

    public MedEntity() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }

    public List<String> getPmids() {
        return pmids;
    }

    public void setPmids(List<String> pmids) {
        this.pmids = pmids;
    }

    public HashMap<String, List<Article>> getPublicationsByYear() {
        return publicationsByYear;
    }

    public void setPublicationsByYear(HashMap<String, List<Article>> publicationsByYear) {
        this.publicationsByYear = publicationsByYear;
    }

    public HashMap<String, HashMap<String, Integer>> getKeywordsByYear() {
        return keywordsByYear;
    }

    public void setKeywordsByYear(HashMap<String, HashMap<String, Integer>> keywordsByYear) {
        this.keywordsByYear = keywordsByYear;
    }

    public HashMap<String, List<String>> getPmidByAffiliation() {
        return pmidByAffiliation;
    }

    public void setPmidByAffiliation(HashMap<String, List<String>> pmidByAffiliation) {
        this.pmidByAffiliation = pmidByAffiliation;
    }

    public HashMap<String, Integer> getAffiliationTotalPapers() {
        return affiliationTotalPapers;
    }

    public void setAffiliationTotalPapers(HashMap<String, Integer> affiliationTotalPapers) {
        this.affiliationTotalPapers = affiliationTotalPapers;
    }

    public List<String> getRelatedExpert() {
        List<String> res = new LinkedList<>();
        for (AuthorInformation a : authors) {
            res.add(a.getFullName());
        }
        return res;
    }

    public List<AuthorInformation> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorInformation> authors) {
        this.authors = authors;
    }

    public List<String> getRelatedArticles() {
        List<String> res = new LinkedList<>();
        for (String year : publicationsByYear.keySet()) {
            for (Article a : publicationsByYear.get(year)) {
                res.add(a.getArticleTitle());
            }
        }
        return res;
    }

    //每年所有文章的被引总和
    public Map<String, Integer> getCitationByYear() {
        HashMap<String, Integer> res = new HashMap<>();
        for (String year : publicationsByYear.keySet()) {
            int ref = 0;
            for (Article a : publicationsByYear.get(year)) {
                if (!pmidCitation.containsKey(a.getPMID())) continue;
                ref += pmidCitation.get(a.getPMID());
            }
            res.put(year, ref);
        }
        return res;
    }

    public HashMap<String, Integer> getPmidCitation() {
        return pmidCitation;
    }

    public void setPmidCitation(HashMap<String, Integer> pmidCitation) {
        this.pmidCitation = pmidCitation;
    }

    public HashMap<String, Integer> getKeywordsCount() {
        HashMap<String, Integer> keywordsCount = new HashMap<>();
        for (String year : keywordsByYear.keySet()) {
            for (String keyword : keywordsByYear.get(year).keySet()) {
                if (!keywordsCount.containsKey(keyword)) {
                    keywordsCount.put(keyword, keywordsByYear.get(year).get(keyword));
                } else {
                    keywordsCount.put(keyword, keywordsCount.get(keyword) + keywordsByYear.get(year).get(keyword));
                }
            }
        }
        return keywordsCount;
    }

    public HashMap<String, Double> getAffiliationIndex() {
        HashMap<String, Double> res = new HashMap<>();
        for (String org : affiliationTotalPapers.keySet()) {
            if (!pmidByAffiliation.containsKey(org) || !affiliationTotalPapers.containsKey(org)) continue;
            res.put(org, (double) pmidByAffiliation.get(org).size() / affiliationTotalPapers.get(org));
        }
        return res;
    }

    public HashMap<String, Integer> getExpertInfluence() {
        HashMap<String, Integer> res = new HashMap<>();
        for (AuthorInformation au : authors) {
            if (!pmidByAid.containsKey(au.getAid())) continue;
            res.put(au.getFullName(), pmidByAid.get(au.getAid()).size());
        }
        return res;
    }

    public HashMap<String, List<String>> getPmidByAid() {
        return pmidByAid;
    }

    public void setPmidByAid(HashMap<String, List<String>> pmidByAid) {
        this.pmidByAid = pmidByAid;
    }

    public HashMap<String, List<String>> getAuthorLink() {
        return authorLink;
    }

    public void setAuthorLink(HashMap<String, List<String>> authorLink) {
        this.authorLink = authorLink;
    }
}

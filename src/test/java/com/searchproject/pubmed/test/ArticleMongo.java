package com.searchproject.pubmed.test;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "article")
public class ArticleMongo {
    private List<String> ref;

    private String pubyear;

    private List<String> keywords;

    @Id
    private ObjectId _id;
    private Integer pmid;

    private String title;

    private List<String> authors;

    private List<String> affiliations;

    public void setRef(List<String> ref){
        this.ref = ref;
    }
    public List<String> getRef(){
        return this.ref;
    }
    public void setPubyear(String pubyear){
        this.pubyear = pubyear;
    }
    public String getPubyear(){
        return this.pubyear;
    }
    public void setKeywords(List<String> keywords){
        this.keywords = keywords;
    }
    public List<String> getKeywords(){
        return this.keywords;
    }
    public void setPmid(Integer pmid){
        this.pmid = pmid;
    }
    public Integer getPmid(){
        return this.pmid;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setAuthors(List<String> authors){
        this.authors = authors;
    }
    public List<String> getAuthors(){
        return this.authors;
    }
    public void setAffiliations(List<String> affiliations){
        this.affiliations = affiliations;
    }
    public List<String> getAffiliations(){
        return this.affiliations;
    }
}

package com.searchproject.pubmed.Bean;

import com.searchproject.pubmed.Bean.auxiliary.AffiliationSimpleMongo;
import com.searchproject.pubmed.Bean.auxiliary.CoAuthorMongo;
import com.searchproject.pubmed.Bean.auxiliary.KeywordCount;
import com.searchproject.pubmed.Bean.auxiliary.PmidMongo;
import com.searchproject.pubmed.Bean.json.ExpertAtlasData;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "expert")
public class ExpertMongo {
    @Id
    private ObjectId _id;
    private long aid;
    private ArrayList<AffiliationSimpleMongo> affiliations;
    private ArrayList<KeywordCount> all_keywords;
    private int citation;
    private  ArrayList<CoAuthorMongo> coAuthors;
    private ArrayList<String> country;
    private ArrayList<String> department;
    private String forename;
    private String fullname;
    private String hIndex;
    private String lastname;
    private ArrayList<PmidMongo> pmids;
    private ArrayList<String> zipcode;
    private ExpertAtlasData relation;
//    static ExpertMongo of(ObjectId _id,Long aid,ArrayList<AffiliationSimpleMongo> affiliations,ArrayList<KeywordCount> all_keywords,int citation,
//                          ArrayList<String> country,ArrayList<String> department,String forename,String fullname,String hIndex,String lastname,ArrayList<PmidMongo> pmids,ArrayList<String> zipcode,ExpertAtlasData relation){
//        return new ExpertMongo(_id,aid,affiliations,all_keywords,citation,null,country,department,forename,fullname,hIndex,lastname,
//                pmids,zipcode,relation);
//    }
//    public ExpertMongo(){coAuthors=new ArrayList<>();}
//    public ExpertMongo withCoAuthors(ArrayList<CoAuthorMongo>coAuthors){
//        return new ExpertMongo(this.aid,this.affiliations,this.all_keywords,this.citation,coAuthors,this.country,this.department,this.fullname,this.hIndex,
//                this.pmids,this.zipcode,this.relation);
//    }
//    @PersistenceConstructor
//    public ExpertMongo(ObjectId _id,Long aid,ArrayList<AffiliationSimpleMongo> affiliations,ArrayList<KeywordCount> all_keywords,int citation,
//   ArrayList<String> country,ArrayList<String> department,String forename,String fullname,String hIndex,String lastname,ArrayList<PmidMongo> pmids,ArrayList<String> zipcode,ExpertAtlasData relation ){
//        this._id=_id;
//        this.aid=aid;
//        this.affiliations=affiliations;
//        this.all_keywords=all_keywords;
//        this.citation=citation;
////        if(coAuthors==null)this.coAuthors=new ArrayList<>();
////        else this.coAuthors=coAuthors;
//        this.forename=forename;
//        this.lastname=lastname;
//        this.country=country;
//        this.department=department;
//        this.fullname=fullname;
//        this.hIndex=hIndex;
//        this.pmids=pmids;
//        this.zipcode=zipcode;
//        this.relation=relation;
//    }
}

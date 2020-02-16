package com.searchproject.pubmed.Bean;

import com.searchproject.pubmed.Bean.auxiliary.AuthorSimpleMongo;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "article")
public class ArticleMongo {
    private List<String> ref;

    private String pubyear;

    private List<String> keywords;

    @Id
    private ObjectId _id;
    private Integer pmid;

    private String title;


    private List<String> affiliations;

    private List<AuthorSimpleMongo> authors;
    private String abstract_text;
    private String pubday;
    private String pubmonth;
    private String journal_title;

}

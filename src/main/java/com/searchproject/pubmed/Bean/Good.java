package com.searchproject.pubmed.Bean;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.util.List;

@Data
@Document(collection = "gf_goods")
public class Good {

    @Id
    private transient ObjectId _id;
    private String url;
    private String name;
    private String imgName;
    private List<String> data;
}

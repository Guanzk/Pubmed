package com.searchproject.pubmed.Bean.json;

import com.google.gson.Gson;
import com.searchproject.pubmed.Bean.EntityMongo;
import lombok.Data;

@Data
public class SimpleEntity {
    private String name;
    private String type;

    public SimpleEntity(EntityMongo entity) {
        setName(entity.getName());
        Gson gson=new Gson();
        setType(gson.toJson(entity.getTypes()));
    }
}

package com.searchproject.pubmed.Bean.json;

import com.searchproject.pubmed.Bean.EntityMongo;
import lombok.Data;

@Data
public class EntityInfoCard {
    private String name;
    private String type;
    private String description="";

    public EntityInfoCard(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public EntityInfoCard(EntityMongo entity) {
        this.name=entity.getName();
        if(entity.getTypes().size()>0)this.type=entity.getTypes().get(0);
    }
}

package com.searchproject.pubmed.config;

import com.searchproject.pubmed.Bean.EntityMongo;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Component
public class EntityBeforeConvertListener extends AbstractMongoEventListener<EntityMongo> {
    @Override
    public void onAfterLoad(AfterLoadEvent<EntityMongo>event){
        Document document=event.getDocument();
        if(document.containsKey("topic")){
            ArrayList<Document> topics= (ArrayList<Document>) document.get("topic");
            for(Document d:topics){
                d.put("topic",d.get("name"));
                d.remove("name");
            }

            document.put("topic_distribution",document.get("topic"));
            document.remove("topic");
        }
    }
}

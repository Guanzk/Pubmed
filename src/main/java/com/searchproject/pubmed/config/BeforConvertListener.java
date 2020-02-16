package com.searchproject.pubmed.config;

import com.searchproject.pubmed.Bean.ExpertMongo;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterLoadEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class BeforConvertListener  extends AbstractMongoEventListener<ExpertMongo>{
    @Override
    public void onAfterLoad(AfterLoadEvent<ExpertMongo> event) {
        super.onAfterLoad(event);
        Document document=event.getDocument();

        if(!document.containsKey("coAuthors")){
            document.put("coAuthors",new ArrayList<>());
            log.debug("缺少coAuthors"+document.toJson());
        }
    }
}
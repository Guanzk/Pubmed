package com.searchproject.pubmed.Bean.json;

import com.google.gson.annotations.SerializedName;
import com.searchproject.pubmed.Bean.EntityMongo;
import com.searchproject.pubmed.util.EntityHelper;
import lombok.Data;

@Data
public class Topic_pie {
    @SerializedName("Topic")
    private Distribution topic;

    public Topic_pie(EntityMongo entity) {
        topic=new Distribution();
        topic.setTitle("Related topic");
        topic.setData(EntityHelper.getTopKTopicMap(10,entity));
    }
}

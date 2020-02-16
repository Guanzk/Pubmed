package com.searchproject.pubmed.Bean;

import com.searchproject.pubmed.Bean.auxiliary.*;
import com.searchproject.pubmed.Bean.json.Node;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "entity_simple")
public class EntityMongo {
    @Id
    private ObjectId _id;
    private List<Node>country_distribution;
    private PaperCount paper_count_by_year;
    private List<RelatedAffiliation> related_affiliations;
    private List<String> types;
    private String name;
    private CitationCount citation_count_by_year;
    private List<TopicDistribution> topic_distribution;
    private ExpertRelation expert_relation;
}

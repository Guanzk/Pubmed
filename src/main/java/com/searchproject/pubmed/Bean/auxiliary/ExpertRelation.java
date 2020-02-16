package com.searchproject.pubmed.Bean.auxiliary;

import com.searchproject.pubmed.Bean.json.Link;
import lombok.Data;

import java.util.List;

@Data
public class ExpertRelation {
    private List<EntityNode>detail;
    private List<Link>link;
}

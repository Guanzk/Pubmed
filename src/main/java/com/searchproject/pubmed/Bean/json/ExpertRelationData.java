package com.searchproject.pubmed.Bean.json;

import com.searchproject.pubmed.Bean.auxiliary.EntityNode;
import lombok.Data;

import java.util.List;

@Data
public class ExpertRelationData {
    private  List<Node> node;
    private List<Link>link;
    private List<EntityNode>detail;

}

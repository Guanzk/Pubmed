package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.List;

@Data
public class ExpertAtlasData {
    private List<Node> node;
    private List<Link>link;
}

package com.searchproject.pubmed.Bean.auxiliary;

import lombok.Data;

import java.util.List;

@Data
public class EntityNode {
    private int influence;
    private List<String> technogy;
    private String name;
}

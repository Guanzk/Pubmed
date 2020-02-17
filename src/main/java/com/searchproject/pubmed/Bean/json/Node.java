package com.searchproject.pubmed.Bean.json;

import lombok.Data;

@Data
public class Node {
    private String name;
    private Integer value;

    public Node(String fullname, int citation) {
        setName(fullname);
        setValue(citation);
    }
    public Node(){}

}

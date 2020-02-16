package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.HashMap;

@Data
public class Distribution {
    private String title;
    private HashMap<String,Integer>data;
}

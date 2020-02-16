package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.HashMap;

@Data
public class Organzation {
    private String title;
    private HashMap<String,Double>data;
}

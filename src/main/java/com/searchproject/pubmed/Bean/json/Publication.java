package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.HashMap;

@Data
public class Publication {
    private String title;
    private HashMap<String,Integer> data;

    public Publication(String publication, HashMap<String, Integer> publicationDistribution) {
        setTitle(publication);
        setData(publicationDistribution);
    }
}

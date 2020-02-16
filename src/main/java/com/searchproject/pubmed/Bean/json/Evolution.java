package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.List;

@Data
public class Evolution {
    private String title;
    private List<List<String>>data;

    public Evolution(String title, List<List<String>> data) {
        setTitle(title);
        setData(data);
    }
}

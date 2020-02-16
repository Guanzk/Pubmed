package com.searchproject.pubmed.Bean.auxiliary;

import lombok.Data;

import java.util.List;

@Data
public class KeywordCount {
    private int year;
    private List<String>keywords;
}

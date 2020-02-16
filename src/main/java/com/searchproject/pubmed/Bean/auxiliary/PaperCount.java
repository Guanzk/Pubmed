package com.searchproject.pubmed.Bean.auxiliary;

import lombok.Data;

import java.util.List;

@Data
public class PaperCount {
    private List<YearCount> years;
    private int total;
}

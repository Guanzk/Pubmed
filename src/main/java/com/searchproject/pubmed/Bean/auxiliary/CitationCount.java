package com.searchproject.pubmed.Bean.auxiliary;

import com.searchproject.pubmed.Bean.auxiliary.YearCount;
import lombok.Data;

import java.util.List;

@Data
public class CitationCount {
    private int total;
    private List<YearCount> years;
}

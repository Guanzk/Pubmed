package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpertDistributionData {
    private String name;
    List<ExpertDistributionData>children;
    public ExpertDistributionData (){}
    public ExpertDistributionData(String keyword, ArrayList<ExpertDistributionData> children) {
        setName(keyword);
        setChildren(children);
    }
}

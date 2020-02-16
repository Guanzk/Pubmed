package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.HashMap;

@Data
public class Interest_pie {
    private Distribution Distribution;

    public Interest_pie(String title, HashMap<String, Integer> keywordsDistribution) {
        Distribution distribution=new Distribution();
        distribution.setTitle(title);
        distribution.setData(keywordsDistribution);
        setDistribution(distribution);
    }
}

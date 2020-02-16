package com.searchproject.pubmed.Bean.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Experts_distribution_tree {
    @SerializedName("Expert distribution")
    private Expert_distribution expertDistribution;

    public Experts_distribution_tree(String title, ExpertDistributionData data) {
        Expert_distribution expertDistribution=new Expert_distribution();
        expertDistribution.setData(data);
        expertDistribution.setTitle(title);
        setExpertDistribution(expertDistribution);
    }
}

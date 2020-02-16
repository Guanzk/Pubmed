package com.searchproject.pubmed.Bean.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashMap;

@Data
public class Publication_bar {
    @SerializedName("Publication")
    private Publication publication;

    public Publication_bar(String publication, HashMap<String, Integer> publicationDistribution) {
        Publication p=new Publication(publication,publicationDistribution);
        setPublication(p);
    }
}

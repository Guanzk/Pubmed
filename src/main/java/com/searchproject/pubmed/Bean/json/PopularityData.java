package com.searchproject.pubmed.Bean.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.HashMap;

@Data
public class PopularityData {
    @SerializedName("PAPER")
    private HashMap<String,Integer>paper;
    @SerializedName("CITATION")
    private HashMap<String,Integer>citation;
}

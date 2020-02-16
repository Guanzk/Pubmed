package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MultiExpertsInfoCard {
    private List<SimpleExpert> info_card;

    public MultiExpertsInfoCard(ArrayList<SimpleExpert> simpleExperts) {
        setInfo_card(simpleExperts);
    }
}

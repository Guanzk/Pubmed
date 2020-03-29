package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MultiExpertsInfoCard {
    private List<SimpleExpert> info_card;
    private List<SimpleEntity> entities;

    public MultiExpertsInfoCard(List<SimpleExpert> simpleExperts) {
        setInfo_card(simpleExperts);
    }
}

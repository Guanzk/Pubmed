package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpertRoot {
    private Info_card info_card;
    private List<Dimensions_data> dimensions_data;
    private Visual visual;

    public ExpertRoot(ArrayList<Dimensions_data> dimensions_data, Info_card info_card, Visual expertVisual) {
        setVisual(expertVisual);
        setInfo_card(info_card);
        setDimensions_data(dimensions_data);
    }
}

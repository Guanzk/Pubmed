package com.searchproject.pubmed.Bean;

import java.util.List;
public class Root
{
    private Info_card info_card;

    private List<Dimensions_data> dimensions_data;

    public void setInfo_card(Info_card info_card){
        this.info_card = info_card;
    }
    public Info_card getInfo_card(){
        return this.info_card;
    }
    public void setDimensions_data(List<Dimensions_data> dimensions_data){
        this.dimensions_data = dimensions_data;
    }
    public List<Dimensions_data> getDimensions_data(){
        return this.dimensions_data;
    }
}

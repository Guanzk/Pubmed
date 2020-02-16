package com.searchproject.pubmed.Bean.json;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Ins_scholars_relation {
    @SerializedName("Expert atlas")
    private ExpertAtlas expertAtlas;

    public Ins_scholars_relation(ArrayList<Node> nodes, ArrayList<Link> links, String title) {
        ExpertAtlas expertAtlas=new ExpertAtlas();
        ExpertAtlasData expertAtlasData=new ExpertAtlasData();
        expertAtlasData.setLink(links);
        expertAtlasData.setNode(nodes);
        expertAtlas.setData(expertAtlasData);
        expertAtlas.setTitle(title);
        setExpertAtlas(expertAtlas);
    }
    public Ins_scholars_relation(ExpertAtlasData relation,String title){
        ExpertAtlas expertAtlas=new ExpertAtlas();
        expertAtlas.setTitle(title);
        expertAtlas.setData(relation);
        setExpertAtlas(expertAtlas);
    }
}

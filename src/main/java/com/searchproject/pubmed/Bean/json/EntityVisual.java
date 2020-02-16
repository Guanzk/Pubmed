package com.searchproject.pubmed.Bean.json;

import lombok.Data;

@Data
public class EntityVisual {
    private PopularityLinefold popularity_linefold;
    private Topic_pie topic_pie;
    private Ins_drug_radar ins_drug_radar;
    private Related_organizations_bar related_organizations_bar;
    private Expert_atlas_relation expert_atlas_relation;

    public EntityVisual(PopularityLinefold popularity_linefold, Topic_pie topic_pie, Ins_drug_radar ins_drug_radar, Related_organizations_bar related_organizations_bar, Expert_atlas_relation expert_atlas_relation) {
        this.popularity_linefold = popularity_linefold;
        this.topic_pie = topic_pie;
        this.ins_drug_radar = ins_drug_radar;
        this.related_organizations_bar = related_organizations_bar;
        this.expert_atlas_relation = expert_atlas_relation;
    }
}

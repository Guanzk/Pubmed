package com.searchproject.pubmed.Bean.json;

import lombok.Data;

@Data
public class Visual {
    private Publication_bar publication_bar;
    private Interest_pie interest_pie;
    private Evolution_river evolution_river;
    private Experts_distribution_tree experts_distribution_tree;
    private Ins_scholars_relation ins_scholars_relation;

    public Visual(Evolution_river evolution_river, Experts_distribution_tree experts_distribution_tree, Ins_scholars_relation ins_scholars_relation, Interest_pie interest_pie, Publication_bar publication_bar) {
        setPublication_bar(publication_bar);
        setInterest_pie(interest_pie);
        setIns_scholars_relation(ins_scholars_relation);
        setExperts_distribution_tree(experts_distribution_tree);
        setEvolution_river(evolution_river);
    }
}

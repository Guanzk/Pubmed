package com.searchproject.pubmed.Bean.json;

import com.searchproject.pubmed.Bean.EntityMongo;
import com.searchproject.pubmed.Bean.ExpertMongo;
import com.searchproject.pubmed.util.EntityHelper;
import com.searchproject.pubmed.util.ExpertHelper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dimensions_data {
    private String dimension;
    private List<String> data;
    private String graphName;

    public Dimensions_data(String dimension, String graphName, List<String> data) {
        setDimension(dimension);
        setData(data);
        setGraphName(graphName);
    }
    public Dimensions_data(){}

    public static Dimensions_data createInterestDimension(ExpertMongo expert) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(ExpertHelper.getTopKKeywords(5,expert));
        dimensions_data.setGraphName("interest_pie");
        dimensions_data.setDimension("Interest");
        return dimensions_data;
    }

    public static Dimensions_data createPublicationDimension(ExpertMongo expert) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(new ArrayList<String>(){{
            add("paper:"+expert.getPmids().size());
            add("citation:"+expert.getCitation());
            add("H-Index:"+expert.getHIndex());
        }});
        dimensions_data.setGraphName("publication_bar");
        dimensions_data.setDimension("Publication");
        return dimensions_data;
    }

    public static Dimensions_data createEvolutionDimension(ExpertMongo expert) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(ExpertHelper.getTopKKeywords(5,expert));
        dimensions_data.setGraphName("evolution_river");
        dimensions_data.setDimension("Evolution");
        return dimensions_data;
    }

    public static Dimensions_data createTopicDimension(ExpertMongo expert,List<ExpertMongo>relatedExpert) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(ExpertHelper.getKNames(3,relatedExpert));
        dimensions_data.setGraphName("experts_distribution_tree");
        dimensions_data.setDimension("Topic distribution");
        return dimensions_data;
    }

    public static Dimensions_data createPopularityDimension(EntityMongo entity) {
        Dimensions_data dimensions_data=new Dimensions_data();
        List<String>data=new ArrayList<String>(){{
            add("total-paper:"+entity.getPaper_count_by_year().getTotal());
            add("latest-paper:"+ EntityHelper.getLatestYearCount(entity));
            add("hostest-year:"+EntityHelper.getHostestYear(entity));

        }};
        dimensions_data.setData(data);
        dimensions_data.setGraphName("popularity_linefold");
        dimensions_data.setDimension("Popularity");
        return dimensions_data;

    }

    public static Dimensions_data createGeographicDimension(EntityMongo entity) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(EntityHelper.getTopKCountry(5,entity));
        dimensions_data.setGraphName("ins_drug_radar");
        dimensions_data.setDimension("geographic preference");
        return dimensions_data;
    }

    public static Dimensions_data createTopicDimension(EntityMongo entity) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(EntityHelper.getTopKTopic(5,entity));
        dimensions_data.setGraphName("topic_pie");
        dimensions_data.setDimension("TOPIC DISTRIBUTION");
        return dimensions_data;
    }

    public static Dimensions_data createOrganizationDimension(EntityMongo entity) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(EntityHelper.getTopKOrganization(5,entity));
        dimensions_data.setGraphName("related_organizations_bar");
        dimensions_data.setDimension("related organizations");
        return dimensions_data;
    }

    public static Dimensions_data createExpertDimension(EntityMongo entity) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(EntityHelper.getTopKExpert(5,entity));
        dimensions_data.setGraphName("expert_atlas_relation");
        dimensions_data.setDimension("expert atlas");
        return dimensions_data;
    }

    public static Dimensions_data createArticlesDimension(EntityMongo entity) {
        Dimensions_data dimensions_data=new Dimensions_data();
        dimensions_data.setData(new ArrayList<>());//TODO 待完善
        dimensions_data.setGraphName("");
        dimensions_data.setDimension("Related Articles");
        return dimensions_data;
    }
}

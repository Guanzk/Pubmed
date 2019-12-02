package Util;


import Bean.Article;
import Bean.Author;
import Bean.AuthorInformation;
import Bean.MedEntity;
import DAO.AuthorUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

public class MakeJson {
    public static String makeAuthorJson(Author author){



                JsonObject container=new JsonObject();

                //第一版
        JsonObject info_card=new JsonObject();

        info_card.addProperty("name",author.getFullName());
        info_card.addProperty("affiliation",author.getAffiliation());
        info_card.addProperty("country",author.getCountry());
        info_card.addProperty("department",author.getDepartment());


        container.add("info_card",info_card);



        JsonArray dimension_data=new JsonArray();



        JsonObject publication=new JsonObject();
        publication.addProperty("dimension","publication");
        JsonArray publicationData=new JsonArray();
//
//        for(String s:author.getPublications()){
//            publicationData.add(s);
//        }
        publicationData.add("paper:"+author.getPmids().size());
        publication.add("data",publicationData);
        publication.addProperty("graphName","publication_bar");
        dimension_data.add(publication);

        JsonObject interest=new JsonObject();
        interest.addProperty("dimension","Interest");
        JsonArray interestData=new JsonArray();
        List<String>top3Keyword= AuthorUtil.getTopKeywords(author.getKeywordCount(),3);
        top3Keyword.forEach(interestData::add);
        interest.addProperty("graphName","interest_pie");
        interest.add("data",interestData);
        dimension_data.add(interest);

        JsonObject evolution=new JsonObject();
        evolution.addProperty("dimension","Evolution");
        JsonArray evolutionData=new JsonArray();
        top3Keyword.forEach(evolutionData::add);
        evolution.addProperty("graphName","evolution_river");
        evolution.add("data",evolutionData);
        dimension_data.add(evolution);

        JsonObject expertDistribution=new JsonObject();
        expertDistribution.addProperty("dimension","Expert distribution");
        JsonArray expertDistributionData=new JsonArray();
        for(int i=0;i<3&&i<author.getOtherExpert().size();i++){
            expertDistributionData.add(author.getOtherExpert().get(i).getFullName());
        }
        evolution.addProperty("graphName","experts_distribution_tree");
        expertDistribution.add("data",expertDistributionData);
        dimension_data.add(expertDistribution);

        JsonObject articles=new JsonObject();
        articles.addProperty("dimension","Articles");
        JsonArray articlesData=new JsonArray();
        List<String>titles=AuthorUtil.getPublicationTitle(author.getPublicationsByYear());
        for(int i=0;i<5&&i<titles.size();i++) {
            articlesData.add(titles.get(i));
        }
        articles.add("data",articlesData);
        dimension_data.add(articles);

        container.add("dimensions_data",dimension_data);

        JsonObject visual= getAuthorVisualData(author);
        container.add("visual",visual);
//        JsonObject statistics=new JsonObject();
//        statistics.addProperty("dimension","statistics");
//        statistics.addProperty("graphName","publication_linefold");
//        dimension_data.add(statistics);
//        container.add("dimension_data",dimension_data);
//
//        JsonArray graphs=new JsonArray();
//        graphs.add(statistics);
//
//        container.add("graphs",graphs);
//
//        JsonObject visual=new JsonObject();
//        JsonObject publication_linefold=new JsonObject();
//        JsonObject Publication=new JsonObject();
//        Publication.addProperty("title","Generated by year");
//        JsonObject data=new JsonObject();
//        JsonObject amount=new JsonObject();
//        HashMap<String,Integer>pubYearStat=author.getPubYearStat();
//        for(String key:pubYearStat.keySet()){
//            amount.addProperty(key,pubYearStat.get(key));
//        }
//        data.add("amount",amount);
//        Publication.add("data",data);
//        publication_linefold.add("Publication",Publication);
//
//        visual.add("publication_linefold",publication_linefold);
//        container.add("visual",visual);

        return container.toString();
    }

    private static JsonObject getAuthorVisualData(Author author) {
        JsonObject visual=new JsonObject();

        JsonObject publication_bar=new JsonObject();
        JsonObject publication=new JsonObject();
        publication.addProperty("title","Publication");
        JsonObject publicationData=new JsonObject();
        author.getPublicationsByYear().keySet().forEach(year -> publicationData.addProperty(year, author.getPublicationsByYear().get(year).size()));
        publication.add("data",publicationData);
        publication_bar.add("Publication",publication);
        visual.add("publication_bar",publication_bar);

        JsonObject interestPie=new JsonObject();
        JsonObject distribution=new JsonObject();
        distribution.addProperty("title","Interest distribution");
        JsonObject distributionData=new JsonObject();
        List<Map.Entry<String,Integer>>mapList=new ArrayList<>(author.getKeywordCount().entrySet());
        Collections.sort(mapList,(o1,o2)->o2.getValue()-o1.getValue());
        for(int i=0;i<5&&i<mapList.size();i++) {
            distributionData.addProperty(mapList.get(i).getKey(),mapList.get(i).getValue());
        }
        distribution.add("data",distributionData);
        interestPie.add("Distribution",distribution);
        visual.add("interest_pie",interestPie);

        JsonObject evolutionRiver=new JsonObject();
        JsonObject evolution=new JsonObject();
        evolution.addProperty("title","Research Evolution");
        JsonArray evolutionData=new JsonArray();
        for(String year:author.getKeywordsByYear().keySet()){

            for(String keywod:author.getKeywordsByYear().get(year).keySet()){
                JsonArray d=new JsonArray();
                d.add(year);
                d.add(author.getKeywordsByYear().get(year).get(keywod));
                d.add(keywod);
                evolutionData.add(d);
            }
        }
        evolution.add("data",evolutionData);
        evolutionRiver.add("Evolution",evolution);
        visual.add("evolution_river",evolutionRiver);

        JsonObject expertsDistributionTree=getExpertsDistributionJson(author);
        visual.add("experts_distribution_tree",expertsDistributionTree);
        return visual;

    }

    private static JsonObject getExpertsDistributionJson(Author author) {
        JsonObject expertsDistributionTree=new JsonObject();
        JsonObject expertDistribution=new JsonObject();
        expertDistribution.addProperty("title", "Expert distribution");
        JsonObject expertDistributionData=new JsonObject();
        expertDistributionData.addProperty("name","expert");
        JsonArray chrildren=new JsonArray();
        List<Author>otherAuthors=author.getOtherExpert();
        for(int i=0;i<10&&i<otherAuthors.size();i++){
            JsonObject c=new JsonObject();
            c.addProperty("name",otherAuthors.get(i).getFullName());
            List<String>top5Keywords=AuthorUtil.getTopKeywords(author.getKeywordCount(),5);
            JsonArray key=new JsonArray();
            for(int j=0;i<top5Keywords.size();i++){
                JsonObject sub=new JsonObject();
                sub.addProperty("name",top5Keywords.get(i));
                sub.add("children",new JsonArray());
                key.add(sub);
            }
            c.add("children",key);
            chrildren.add(c);
        }
        expertDistributionData.add( "children",chrildren);
        expertDistribution.add("data",expertDistributionData);
        expertsDistributionTree.add("Expert distribution",expertDistribution);
        return expertsDistributionTree;

    }


    public static String makeEntityJson(MedEntity entity){
        List<Map.Entry<String,List<Article>>> articleEntryList=new LinkedList<Map.Entry<String,List<Article>>>(){{
            addAll(entity.getPublicationsByYear().entrySet());
        }};
        JsonObject container=new JsonObject();
        JsonObject info_card=new JsonObject();
        JsonArray dimention_data=new JsonArray();


        info_card.addProperty("name",entity.getName());
        info_card.addProperty("type",entity.getTypes().toString());
        info_card.addProperty("description",entity.getDescription());
        container.add("info_card",info_card);

        JsonObject publicatiions=new JsonObject();
        publicatiions.addProperty("dimension","publication");
        JsonArray publicationData=new JsonArray();
        publicationData.add("total-paper:"+entity.getPmids().size());
        Collections.sort(articleEntryList,(o1, o2) -> {return Integer.valueOf(o2.getKey())-Integer.valueOf(o1.getKey());});
        publicationData.add("latest-paper:"+articleEntryList.get(0).getValue().size());
        publicationData.add("promising: +20%");
        Collections.sort(articleEntryList,(o1, o2) -> {return o2.getValue().size()-o1.getValue().size();});
        publicationData.add("hottest-year:"+articleEntryList.get(0).getKey());
        publicatiions.add("data",publicationData);
        publicatiions.addProperty( "graphName","popularity_linefold");
        dimention_data.add(publicatiions);

        JsonObject topicDistribution=new JsonObject();
        topicDistribution.addProperty("dimension","TOPIC DISTRIBUTION");
        JsonArray topicDistributionData=new JsonArray();
        List<String>top5Keywords=ArticleUtil.getTopKeywords(entity.getKeywordsByYear(),5);
        for(int i=0;i<top5Keywords.size();i++){
            topicDistributionData.add(top5Keywords.get(i));
        }
        topicDistribution.add("data",topicDistributionData);
        topicDistribution.addProperty("graphName","topic_pie");
        dimention_data.add(topicDistribution);

        JsonObject relatedOrganizations=new JsonObject();
        JsonArray relatedOrganizationData=new JsonArray();
        List<String>affiliations=new LinkedList<>(entity.getPmidByAffiliation().keySet());
        for(int i=0;i<affiliations.size()&&i<5;i++){
            relatedOrganizationData.add(affiliations.get(i));
        }
        relatedOrganizations.addProperty("dimension","related organizations");
        relatedOrganizations.add( "data",relatedOrganizationData);
        relatedOrganizations.addProperty("graphName", "related_organizations_bar");
        dimention_data.add(relatedOrganizations);

        JsonObject expertAtlas=new JsonObject();
        expertAtlas.addProperty("dimension","expert atlas");
        JsonArray expertAtlasData=new JsonArray();
        List<String>expertNames=entity.getRelatedExpert();
        for(int i=0;i<expertNames.size();i++){
            relatedOrganizationData.add(expertNames.get(i));
        }
        expertAtlas.addProperty("graphName","expert_atlas_relation");
        expertAtlas.add("data",expertAtlasData);
        dimention_data.add(expertAtlas);


        JsonObject relatedArtilces=new JsonObject();
        relatedArtilces.addProperty("dimension","Related Articles");
        JsonArray relatedArticleData=new JsonArray();
        List<String>relatedArticlesName=entity.getRelatedArticles();
        for(int i=0;i<5&&i<relatedArticlesName.size();i++){
            relatedArticleData.add(relatedArticlesName.get(i));
        }
        relatedArtilces.add("data",relatedArticleData);
        dimention_data.add(relatedArtilces);
        container.add("dimenstions_data",dimention_data);

        JsonObject visual=getEntityVisualJson(entity);
        container.add("visual",visual);

        return container.toString();
    }

    private static JsonObject getEntityVisualJson(MedEntity entity) {
        JsonObject visual=new JsonObject();

        JsonObject popularityLinefold=new JsonObject();
        JsonObject popularity=new JsonObject();
        popularity.addProperty("title","Popularity of bio entitiy");
        JsonObject popularityData=new JsonObject();
        JsonObject paper=new JsonObject();
        for(String year:entity.getPublicationsByYear().keySet()){
            paper.addProperty(year,entity.getPublicationsByYear().get(year).size());
        }
        popularityData.add("PAPER",paper);
        JsonObject citation=new JsonObject();
        for(String year:entity.getCitationByYear().keySet()){
            citation.addProperty(year,entity.getCitationByYear().get(year));
        }
        popularityData.add("CITATION",citation);
        popularity.add("data",popularityData);
        popularityLinefold.add("Popularity",popularity);
        visual.add("popularity_linefold",popularityLinefold);

        JsonObject topicPie=new JsonObject();
        JsonObject topic=new JsonObject();
        topic.addProperty("title","Related topic");
        JsonObject topicData=new JsonObject();
        HashMap<String,Integer>keywordsCount=entity.getKeywordsCount();
        for(String keyword:keywordsCount.keySet()){
            topicData.addProperty(keyword,keywordsCount.get(keyword));
        }
        topic.add("data",topicData);
        topicPie.add("Topic",topic);
        visual.add("topic_pie",topicPie);

        JsonObject relatedOrganizationBar=new JsonObject();
        JsonObject organization=new JsonObject();
        organization.addProperty("title","Articles related / total articles");
        JsonObject organizationData=new JsonObject();
        HashMap<String,Integer>affiliationIndex=entity.getAffiliationIndex();
        for(String affiliation:affiliationIndex.keySet()){
            organizationData.addProperty(affiliation,affiliationIndex.get(affiliation));
        }
        organization.add("data",organizationData);
        relatedOrganizationBar.add("Organization",organization);
        visual.add("related_organizations_bar",relatedOrganizationBar);

        JsonObject expertAtlasRelation=new JsonObject();
        JsonObject expert=new JsonObject();
        expert.addProperty("title","Expert atlas and influence");
        JsonObject expertData=new JsonObject();
        JsonArray node=new JsonArray();
        HashMap<String,Integer>expertInfluence=entity.getExpertInfluence();
        for(String expertName:expertInfluence.keySet()){
            JsonObject e=new JsonObject();
            e.addProperty("name",expertName);
            e.addProperty("value",expertInfluence.get(expertName));
            node.add(e);
        }
        expertData.add("node",node);
        JsonArray link=new JsonArray();
        HashMap<String,List<String>>authorLink=entity.getAuthorLink();
        for(String authorName:authorLink.keySet()){
            JsonObject j=new JsonObject();
            for(String target:authorLink.get(authorName)){
                j.addProperty("source",authorName);
                j.addProperty("target",target);
            }
            link.add(j);
        }
        expertData.add("link",link);
        expert.add("data",expertData);
        JsonArray detail=new JsonArray();
        List<AuthorInformation> relatedAuthor=entity.getAuthors();
        HashMap<String, List<String>> pmidByAid=entity.getPmidByAid();
        for(AuthorInformation a:relatedAuthor){
            if(!pmidByAid.containsKey(a.getAid()))continue;
            JsonObject j=new JsonObject();
            j.addProperty("influence",pmidByAid.get(a.getAid()).size());
            JsonArray tech=new JsonArray();
            for(String interest:a.getKeywords()){
                tech.add(interest);
            }
            j.add("technology",tech);
            j.addProperty("name",a.getFullName());
            detail.add(j);
        }
        expert.add("detail",detail);
        expertAtlasRelation.add("Expert",expert);
        visual.add("expert_atlas_relation",expertAtlasRelation);

        return visual;



    }

    private static  String hashMapListToString(String type, HashMap<String,List<String>> map){
       StringBuilder sb=new StringBuilder();
       if(map.get(type)!=null){
           for(String s:map.get(type)){

                   sb.append(s+",");
           }
       }
        if(sb.length()>=1) {
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
//        Author a=new Author(){{
//            setAffiliation("ndiana University Bloomington");
//            setEmail("djwild@indiana.edu");
//            setFullName("David J. Wild");
//            setPublications(new ArrayList<String>(){{
//                add("Optimizing drug–target interaction prediction based on random walk on heterogeneous networks");
//                add("RepTB: a gene ontology based drug repurposing approach for tuberculosis");
//            }});
//            setSpecies(new HashMap<String,List<String>>(){{
//                put("gene",new ArrayList<String>(){{
//                    add("FLT3");
//                    add("GSK3β");
//                }});
//                put("drug",new ArrayList<String>(){{
//                    add("Fluoroquinolones");
//                    add("Gatifloxacin");
//                }});
//
//
//            }});
//
//        }};

//        System.out.println(makeAuthorJson(a));

    }
}

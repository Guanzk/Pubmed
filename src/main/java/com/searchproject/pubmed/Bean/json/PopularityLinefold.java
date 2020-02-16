package com.searchproject.pubmed.Bean.json;

import com.searchproject.pubmed.Bean.EntityMongo;
import com.searchproject.pubmed.Bean.auxiliary.YearCount;
import lombok.Data;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;

@Data
public class PopularityLinefold {
    private Popularity Popularity;
    public PopularityLinefold(EntityMongo entity){
        Popularity=new Popularity();
        Popularity.setTitle("Popularity of bio entitiy");
        PopularityData popularityData=new PopularityData();
        HashMap<String,Integer>citaion=new HashMap<>();
        for(YearCount year:entity.getCitation_count_by_year().getYears()){
            citaion.put(String.valueOf(year.getYear()),year.getCount());
        }
        HashMap<String,Integer>paper=new HashMap<>();
        for(YearCount year:entity.getPaper_count_by_year().getYears()){
            paper.put(String.valueOf(year.getYear()),year.getCount());
        }
        popularityData.setCitation(citaion);
        popularityData.setPaper(paper);
        Popularity.setData(popularityData);

    }
}

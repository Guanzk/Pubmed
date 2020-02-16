package com.searchproject.pubmed.Bean.json;

import com.searchproject.pubmed.Bean.EntityMongo;
import lombok.Data;

@Data
public class Ins_drug_radar {
    private Country country;

    public Ins_drug_radar(EntityMongo entity) {
        country=new Country();
        country.setTitle("research distribution");
        CountryData countryData=new CountryData();
        countryData.setItem(entity.getCountry_distribution());
        country.setData(countryData);
    }
}

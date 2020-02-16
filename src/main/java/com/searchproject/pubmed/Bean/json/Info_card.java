package com.searchproject.pubmed.Bean.json;

import com.searchproject.pubmed.Bean.ExpertMongo;
import lombok.Data;

@Data
public class Info_card {
    private String name="";
    private String country="";
    private String affiliation="";
    private String department="";
    public Info_card(ExpertMongo expert){
        if(expert.getAffiliations().size()>0)setAffiliation(expert.getAffiliations().get(0).getAffiliation());
        if(expert.getCountry().size()>0)setCountry(expert.getCountry().get(0));
        if(expert.getDepartment().size()>0)setDepartment(expert.getDepartment().get(0));
        setName(expert.getFullname());
    }

}

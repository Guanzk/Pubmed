package com.searchproject.pubmed.Bean.json;

import com.google.gson.annotations.SerializedName;
import com.searchproject.pubmed.Bean.EntityMongo;
import com.searchproject.pubmed.util.EntityHelper;
import lombok.Data;

@Data
public class Related_organizations_bar {
    @SerializedName("Organization")
    private Organzation organzation;

    public Related_organizations_bar(EntityMongo entity) {
        organzation=new Organzation();
        organzation.setTitle("Articles related / total articles");
        organzation.setData(EntityHelper.getTopKOrganizationMap(5,entity));
    }
}

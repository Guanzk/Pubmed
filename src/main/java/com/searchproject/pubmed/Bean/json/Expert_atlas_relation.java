package com.searchproject.pubmed.Bean.json;


import com.searchproject.pubmed.Bean.EntityMongo;
import com.searchproject.pubmed.util.EntityHelper;
import lombok.Data;

@Data
public class Expert_atlas_relation {
    private ExpertRelation Expert;

    public Expert_atlas_relation(EntityMongo entity) {
        Expert=new ExpertRelation();
        Expert.setTitle("Expert atlas and influence");
        ExpertRelationData expertRelationData=new ExpertRelationData();
        expertRelationData.setLink(entity.getExpert_relation().getLink());
        expertRelationData.setNode(EntityHelper.getNode(entity));
        expertRelationData.setDetail(entity.getExpert_relation().getDetail());
        Expert.setData(expertRelationData);
    }
}

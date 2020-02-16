package com.searchproject.pubmed.Bean.json;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EntityJson {
    private List<EntityInfoCard> info_card;
    private List<Dimensions_data> dimensions_data;
    private EntityVisual visual;

    public EntityJson(EntityInfoCard info_card, List<Dimensions_data> dimensions_data, EntityVisual visual) {
        this.info_card = new ArrayList<EntityInfoCard>(){{
            add(info_card);
        }};
        this.dimensions_data = dimensions_data;
        this.visual = visual;
    }
}

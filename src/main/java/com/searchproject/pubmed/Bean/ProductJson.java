package com.searchproject.pubmed.Bean;

import lombok.Data;

import java.util.List;

@Data
public class ProductJson {
    class Info_card{
        public Info_card(String name, String type, String description) {
            this.name = name;
            this.type = type;
            this.description = description;
        }

        private String name;
        private String type;
        private String description;

    }
    private List<Good> diemsions_data;
    private Info_card info_card;
    public ProductJson(List<Good> diemsions_data, String name, String type, String description) {
        this.diemsions_data = diemsions_data;
        this.info_card = new Info_card(name,  type,  description);
    }



}

package com.searchproject.pubmed.Bean.json;

import lombok.Data;

@Data
public class Evolution_river {
    private Evolution Evolution;

    public Evolution_river(Evolution evolution) {
        setEvolution(evolution);
    }
}

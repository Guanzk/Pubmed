package com.searchproject.pubmed.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MedEntityDao {
    private static ReadDataFromRedis redis = new ReadDataFromRedis();

    public static Set<String> getEntityName(String query) {
        return ReadDataFromRedis.getTypeFromEntity(redis, query);
    }

    public static List<String> getPmids(String query) {
        return new LinkedList<String>() {{
            addAll(ReadDataFromRedis.getPMIDfromEntity_Pool(redis, query));
        }};
    }
}

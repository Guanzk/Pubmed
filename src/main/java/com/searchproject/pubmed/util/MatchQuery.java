package com.searchproject.pubmed.util;

import java.util.ArrayList;
import java.util.List;

public class MatchQuery {
    private List<EntityMap> list;

    public MatchQuery(List<EntityMap> list) {
        this.list = list;
    }

    public List<QueryClass> ngramMatch(String str) {
        List<QueryClass> result = new ArrayList<>();
        if (str.equals("") == false) {
            String[] input = str.split(" ");
            QueryClass qclass = leftMax(input);
            result.add(qclass);
        }
        return result;
    }

    private QueryClass leftMax(String[] input) {
        QueryClass qc = new QueryClass();
        qc.setMaxQuery("");
        qc.setQueryType("");
        int len = input.length;
        while (len > 0) {
            String subStr = "";
            if (len <= 20) {
                subStr = getArrString(input, 0, len);
            } else {
                subStr = getArrString(input, 0, 21);
            }
            String qt = getQueryType(subStr);
            if (qt.equals("") == false) {
                qc.setQueryType(qt);
                qc.setMaxQuery(subStr);
                return qc;
            }
            len--;

        }
        return qc;

    }

    private String getQueryType(String subStr) {
        String result = "";
        for (EntityMap map : list) {
            if (map.getMap().get(subStr) != null) {
                result = map.getType();
            }
        }
        return result;
    }

    private String getArrString(String[] input, int lo, int hi) {
        String str = "";
        StringBuilder sb = new StringBuilder();
        for (int i = lo; i < hi; i++) {
            if (str.equals("")) {
                str = input[i];
                sb.append(input[i]);
            } else {
                sb.append(" ");
                sb.append(input[i]);
            }
        }
        return sb.toString();
    }
}

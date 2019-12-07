package com.searchproject.pubmed.util;

import com.searchproject.pubmed.Bean.Article;

import java.util.*;

public class ArticleUtil {

    public static Map<String, List<Article>> sortArticlesByYear(HashMap<String, List<Article>> publicationsByYear) {
        Map<String, List<Article>> res = new TreeMap<String, List<Article>>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.parseInt(o2) - Integer.parseInt(o1);
            }
        }
        );
        res.putAll(publicationsByYear);
        return res;


    }

    public static List<String> getTopKeywords(HashMap<String, HashMap<String, Integer>> keywordsByYear, int i) {
        Map<String, Integer> keywordsCount = new TreeMap<>();
        for (String year : keywordsByYear.keySet()) {
            for (String keyword : keywordsByYear.get(year).keySet()) {
                if (keywordsCount.containsKey(keyword)) {
                    keywordsCount.put(keyword, keywordsCount.get(keyword) + keywordsByYear.get(year).get(keyword));
                } else {
                    keywordsCount.put(keyword, keywordsByYear.get(year).get(keyword));
                }
            }
        }
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(keywordsCount.entrySet());
        Collections.sort(entryList, (o1, o2) -> {
            return o2.getValue() - o1.getValue();
        });
        List<String> res = new LinkedList<>();
        for (int j = 0; j < i && j < res.size(); j++) {
            res.add(entryList.get(j).getKey());
        }
        return res;


    }
}

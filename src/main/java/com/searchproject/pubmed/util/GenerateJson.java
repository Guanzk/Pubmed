package com.searchproject.pubmed.util;

import com.google.gson.Gson;
import com.searchproject.pubmed.Bean.Good;
import com.searchproject.pubmed.Bean.ProductJson;

import java.util.List;

public class GenerateJson {
    public static String getProductJson(List<Good>goods,String query){
        Gson gson =new Gson();
        Good g1=goods.get(0);
        String description=g1.getData().get(1);
        String type=description.substring(description.indexOf("["),description.indexOf("]")+1);
        ProductJson productJson=new ProductJson(goods,query,type,"");
        return gson.toJson(productJson);
    }
}

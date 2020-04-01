package com.searchproject.pubmed.util;

import com.google.gson.Gson;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

public class ListConverter implements AttributeConverter<List<String>,String> {

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        return new Gson().toJson(strings);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        Gson gson=new Gson();
        List<String>res=gson.fromJson(s,new ArrayList<String>().getClass());
        return null;
    }
}
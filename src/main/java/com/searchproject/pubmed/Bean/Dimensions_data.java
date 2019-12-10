package com.searchproject.pubmed.Bean;


import java.util.List;
public class Dimensions_data {
    private String name;

    private String dimension;

    private List<String> data;

    private String imgName;

    private String url;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getDimension() {
        return this.dimension;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return this.data;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgName() {
        return this.imgName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
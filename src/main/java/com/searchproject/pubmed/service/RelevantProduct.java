package com.searchproject.pubmed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RelevantProduct {
    @Autowired
    private RestTemplate restTemplate;
    public List<String> getProduct(String product){
        String url="http://192.168.1.108:20308/{?}";
        List<String>res=restTemplate.getForObject(url,new ArrayList<>().getClass(),product);
        return res;
    }
}

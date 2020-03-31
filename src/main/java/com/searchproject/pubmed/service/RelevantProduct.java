package com.searchproject.pubmed.service;

import com.google.gson.Gson;
import com.searchproject.pubmed.Bean.Good;
import com.searchproject.pubmed.dao.GoodsRepository;
import com.searchproject.pubmed.util.GenerateJson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class RelevantProduct {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    GoodsRepository goodsRepository;
    @Value("${relatemodel.api}")
    private String url;
    public String getProduct(String product){
//        String url="http://192.168.1.108:20308/{?}";

        url=url+product;
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

// Note: here we are making this converter to process any kind of response,
// not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        String str=restTemplate.getForObject(url,String.class);
        Gson gson=new Gson();

        List<Good>goods=goodsRepository.findByNameIn(gson.fromJson(str,new ArrayList<String>().getClass()));
        log.debug(gson.toJson(goods));
        String res= GenerateJson.getProductJson(goods,product);
        return res;
    }
}

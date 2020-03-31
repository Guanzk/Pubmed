package com.searchproject.pubmed;

import com.google.gson.Gson;
import com.searchproject.pubmed.dao.RedisDao;
import com.searchproject.pubmed.service.RelevantProduct;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PubmedApplicationTests {


    @Autowired
    RedisDao redisDao;
    @Test
    public void Test(){
        redisDao.setDataBase(19);
        Set<String> set=redisDao.getAidSet("fibulin-4 shrna");
        Gson gson=new Gson();
        System.out.println(gson.toJson(set));
    }
    @Autowired
    RelevantProduct relevantProduct;
    @Test
    public void testMongo(){
        System.out.println(relevantProduct.getProduct("apple"));

    }
    @Autowired
    RestTemplate restTemplate;
    @Test
    public void testUrl(){
        String url="http://localhost:20308/苹果";
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

// Note: here we are making this converter to process any kind of response,
// not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        restTemplate.setMessageConverters(messageConverters);
        String str=restTemplate.getForObject(url,String.class);
        System.out.println(str);
    }

}

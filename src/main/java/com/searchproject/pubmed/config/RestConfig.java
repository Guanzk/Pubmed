package com.searchproject.pubmed.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
public class RestConfig {


        @Bean
        public RestTemplate restTemplate(ClientHttpRequestFactory factory){
            RestTemplate restTemplate=new RestTemplate(factory);
            List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
            converters .removeIf(httpMessageConverter -> httpMessageConverter instanceof StringHttpMessageConverter);

            restTemplate.getMessageConverters().set(0,new StringHttpMessageConverter(StandardCharsets.UTF_8));
            return new RestTemplate(factory);
        }

        @Bean
        public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(15000);
            factory.setReadTimeout(5000);
            return factory;
        }

}

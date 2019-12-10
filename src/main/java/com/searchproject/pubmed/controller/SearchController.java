package com.searchproject.pubmed.controller;

import com.searchproject.pubmed.grpc.QueryRequest;
import com.searchproject.pubmed.grpc.QueryResponse;
import com.searchproject.pubmed.grpc.SearchServiceGrpc;
import com.searchproject.pubmed.service.SearchProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
public class SearchController {
    private SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;
    public SearchController(SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub){
        this.searchServiceBlockingStub=searchServiceBlockingStub;
    }
    @RequestMapping(value = "/getPubmed/{query}",method = RequestMethod.GET)
    public String searchPubmed(@PathVariable("query")String query){
        log.info("收到请求"+query);
        long start=System.currentTimeMillis();
        QueryResponse response=this.searchServiceBlockingStub.searchQuery(QueryRequest.newBuilder().setQuery(query).build());
        long end=System.currentTimeMillis();
        log.info("检索:"+query+"完成，用时："+(end-start));
        return response.getResponse();
    }
    @RequestMapping(value="/getECommerce/{product}",method = RequestMethod.GET)
    public String getProductJson(@PathVariable("product")String product){
        return SearchProduct.search(product);
    }
}



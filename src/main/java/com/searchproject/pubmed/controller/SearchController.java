//package com.searchproject.pubmed.controller;
//
//import com.searchproject.pubmed.grpc.QueryRequest;
//import com.searchproject.pubmed.grpc.QueryResponse;
//import com.searchproject.pubmed.grpc.SearchServiceGrpc;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class SearchController {
//    private SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub;
//    public SearchController(SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub){
//        this.searchServiceBlockingStub=searchServiceBlockingStub;
//    }
//    @RequestMapping(value = "/getPubmed/{query}",method = RequestMethod.GET)
//    public String searchPubmed(@PathVariable("query")String query){
//        QueryResponse response=this.searchServiceBlockingStub.searchQuery(QueryRequest.newBuilder().setQuery(query).build());
//        return response.getResponse();
//    }
//}

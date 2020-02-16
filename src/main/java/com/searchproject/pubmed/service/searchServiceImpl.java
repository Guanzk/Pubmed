package com.searchproject.pubmed.service;

import com.searchproject.pubmed.grpc.QueryRequest;
import com.searchproject.pubmed.grpc.QueryResponse;
import com.searchproject.pubmed.grpc.SearchServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GRpcService
public class searchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase  {
    @Autowired
    SearchPubmed searchPubmed;
    @Override
    public void searchQuery(QueryRequest request,
                            StreamObserver<QueryResponse> responseObserver) {
            String query=request.getQuery();
            String json=searchPubmed.processPubmedQuery(query);
            QueryResponse response=QueryResponse.newBuilder().setResponse(json).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
    }
    @Override
    public void searchAid(QueryRequest request,
                            StreamObserver<QueryResponse> responseObserver) {
        String aid=request.getQuery();
        String json=searchPubmed.searchAid(aid);
        QueryResponse response=QueryResponse.newBuilder().setResponse(json).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



}

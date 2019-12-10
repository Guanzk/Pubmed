package com.searchproject.pubmed.service;

import com.searchproject.pubmed.grpc.QueryRequest;
import com.searchproject.pubmed.grpc.QueryResponse;
import com.searchproject.pubmed.grpc.SearchServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class searchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase  {
    @Override
    public void searchQuery(QueryRequest request,
                            StreamObserver<QueryResponse> responseObserver) {
            String query=request.getQuery();
            String json=SearchPubmed.processPubmedSearch(query);
            QueryResponse response=QueryResponse.newBuilder().setResponse(json).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
    }

}

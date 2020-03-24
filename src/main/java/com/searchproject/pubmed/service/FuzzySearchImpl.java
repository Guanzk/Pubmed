package com.searchproject.pubmed.service;

import com.searchproject.pubmed.grpc.FuzzyQueryRequest;
import com.searchproject.pubmed.grpc.FuzzyQueryResponse;
import com.searchproject.pubmed.grpc.FuzzySearchGrpc;
import io.grpc.stub.StreamObserver;

public class FuzzySearchImpl extends FuzzySearchGrpc.FuzzySearchImplBase {
    @Override
    public void search(FuzzyQueryRequest request, StreamObserver<FuzzyQueryResponse> responseObserver){
        matchQuery match=new matchQuery();
        match.ngramMatch(request.getQuery());
        FuzzyQueryResponse response=FuzzyQueryResponse.newBuilder().addAllAidSet(match.getAid()).addAllEntitySet(match.getEntity()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

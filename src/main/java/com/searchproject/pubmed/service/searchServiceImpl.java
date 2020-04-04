package com.searchproject.pubmed.service;

import com.searchproject.pubmed.grpc.*;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;


@Slf4j
@GRpcService
public class searchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase  {
    @Autowired
    SearchPubmed searchPubmed;

@Autowired
ProductSearchGrpc.ProductSearchBlockingStub productSearchBlockingStub;
    @Autowired
    SearchAi searchAi;
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
    @Override
    public void searchPossibleProduct(com.searchproject.pubmed.grpc.QueryRequest request,
                                      io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.QueryResponse> responseObserver) {
      String product=request.getQuery();
      log.info("收到查询商品：{} 请求",product);
      long start=System.currentTimeMillis();
      String res=productSearchBlockingStub.search(ProductRequest.newBuilder().setQuery(product).build()).getResult();
      long end=System.currentTimeMillis();
      log.info("检索完成，用时{}",end-start);
      responseObserver.onNext(QueryResponse.newBuilder().setResponse(res).build());
      responseObserver.onCompleted();
    }
    @Override
    public void searchAi(QueryRequest request,
                          StreamObserver<QueryResponse> responseObserver) {
        String query=request.getQuery();
        String json=searchAi.search(query);
        QueryResponse response=QueryResponse.newBuilder().setResponse(json).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }



}

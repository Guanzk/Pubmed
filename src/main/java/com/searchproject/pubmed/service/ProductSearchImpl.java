package com.searchproject.pubmed.service;

import com.searchproject.pubmed.grpc.ProductResponse;
import com.searchproject.pubmed.grpc.ProductSearchGrpc;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GRpcService
public class ProductSearchImpl extends ProductSearchGrpc.ProductSearchImplBase {
    @Autowired
    RelevantProduct relevantProduct;
    @Override
    public void search(com.searchproject.pubmed.grpc.ProductRequest request,
                       io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.ProductResponse> responseObserver) {
      String query=request.getQuery();
        String res=relevantProduct.getProduct(query);
        responseObserver.onNext(ProductResponse.newBuilder().setResult(res).build());
        responseObserver.onCompleted();
    }
}

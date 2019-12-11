package com.searchproject.pubmed.config;

import com.searchproject.pubmed.grpc.SearchServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
    @Bean
    ManagedChannel channel(@Value("${app-config.grpc-server-name}") String name,
                           @Value("${app-config.grpc-server-port}") Integer port){
        return ManagedChannelBuilder.forAddress(name, port)
                .usePlaintext()
                .build();
    }


    @Bean
    SearchServiceGrpc.SearchServiceBlockingStub searchServiceBlockingStub(ManagedChannel channel){
        return SearchServiceGrpc.newBlockingStub(channel);
    }
}

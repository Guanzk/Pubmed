package com.searchproject.pubmed.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.23.0)",
    comments = "Source: SearchService.proto")
public final class SearchServiceGrpc {

  private SearchServiceGrpc() {}

  public static final String SERVICE_NAME = "com.searchproject.pubmed.grpc.SearchService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.searchproject.pubmed.grpc.QueryRequest,
      com.searchproject.pubmed.grpc.QueryResponse> getSearchQueryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchQuery",
      requestType = com.searchproject.pubmed.grpc.QueryRequest.class,
      responseType = com.searchproject.pubmed.grpc.QueryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.searchproject.pubmed.grpc.QueryRequest,
      com.searchproject.pubmed.grpc.QueryResponse> getSearchQueryMethod() {
    io.grpc.MethodDescriptor<com.searchproject.pubmed.grpc.QueryRequest, com.searchproject.pubmed.grpc.QueryResponse> getSearchQueryMethod;
    if ((getSearchQueryMethod = SearchServiceGrpc.getSearchQueryMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchQueryMethod = SearchServiceGrpc.getSearchQueryMethod) == null) {
          SearchServiceGrpc.getSearchQueryMethod = getSearchQueryMethod =
              io.grpc.MethodDescriptor.<com.searchproject.pubmed.grpc.QueryRequest, com.searchproject.pubmed.grpc.QueryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchQuery"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.searchproject.pubmed.grpc.QueryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.searchproject.pubmed.grpc.QueryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("searchQuery"))
              .build();
        }
      }
    }
    return getSearchQueryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.searchproject.pubmed.grpc.QueryRequest,
      com.searchproject.pubmed.grpc.QueryResponse> getSearchAidMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "searchAid",
      requestType = com.searchproject.pubmed.grpc.QueryRequest.class,
      responseType = com.searchproject.pubmed.grpc.QueryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.searchproject.pubmed.grpc.QueryRequest,
      com.searchproject.pubmed.grpc.QueryResponse> getSearchAidMethod() {
    io.grpc.MethodDescriptor<com.searchproject.pubmed.grpc.QueryRequest, com.searchproject.pubmed.grpc.QueryResponse> getSearchAidMethod;
    if ((getSearchAidMethod = SearchServiceGrpc.getSearchAidMethod) == null) {
      synchronized (SearchServiceGrpc.class) {
        if ((getSearchAidMethod = SearchServiceGrpc.getSearchAidMethod) == null) {
          SearchServiceGrpc.getSearchAidMethod = getSearchAidMethod =
              io.grpc.MethodDescriptor.<com.searchproject.pubmed.grpc.QueryRequest, com.searchproject.pubmed.grpc.QueryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "searchAid"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.searchproject.pubmed.grpc.QueryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.searchproject.pubmed.grpc.QueryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SearchServiceMethodDescriptorSupplier("searchAid"))
              .build();
        }
      }
    }
    return getSearchAidMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SearchServiceStub newStub(io.grpc.Channel channel) {
    return new SearchServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SearchServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new SearchServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SearchServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new SearchServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class SearchServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void searchQuery(com.searchproject.pubmed.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.QueryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchQueryMethod(), responseObserver);
    }

    /**
     */
    public void searchAid(com.searchproject.pubmed.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.QueryResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getSearchAidMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSearchQueryMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.searchproject.pubmed.grpc.QueryRequest,
                com.searchproject.pubmed.grpc.QueryResponse>(
                  this, METHODID_SEARCH_QUERY)))
          .addMethod(
            getSearchAidMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.searchproject.pubmed.grpc.QueryRequest,
                com.searchproject.pubmed.grpc.QueryResponse>(
                  this, METHODID_SEARCH_AID)))
          .build();
    }
  }

  /**
   */
  public static final class SearchServiceStub extends io.grpc.stub.AbstractStub<SearchServiceStub> {
    private SearchServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchServiceStub(channel, callOptions);
    }

    /**
     */
    public void searchQuery(com.searchproject.pubmed.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.QueryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSearchQueryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void searchAid(com.searchproject.pubmed.grpc.QueryRequest request,
        io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.QueryResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSearchAidMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SearchServiceBlockingStub extends io.grpc.stub.AbstractStub<SearchServiceBlockingStub> {
    private SearchServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.searchproject.pubmed.grpc.QueryResponse searchQuery(com.searchproject.pubmed.grpc.QueryRequest request) {
      return blockingUnaryCall(
          getChannel(), getSearchQueryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.searchproject.pubmed.grpc.QueryResponse searchAid(com.searchproject.pubmed.grpc.QueryRequest request) {
      return blockingUnaryCall(
          getChannel(), getSearchAidMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SearchServiceFutureStub extends io.grpc.stub.AbstractStub<SearchServiceFutureStub> {
    private SearchServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private SearchServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SearchServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new SearchServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.searchproject.pubmed.grpc.QueryResponse> searchQuery(
        com.searchproject.pubmed.grpc.QueryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSearchQueryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.searchproject.pubmed.grpc.QueryResponse> searchAid(
        com.searchproject.pubmed.grpc.QueryRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSearchAidMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SEARCH_QUERY = 0;
  private static final int METHODID_SEARCH_AID = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SearchServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SearchServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEARCH_QUERY:
          serviceImpl.searchQuery((com.searchproject.pubmed.grpc.QueryRequest) request,
              (io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.QueryResponse>) responseObserver);
          break;
        case METHODID_SEARCH_AID:
          serviceImpl.searchAid((com.searchproject.pubmed.grpc.QueryRequest) request,
              (io.grpc.stub.StreamObserver<com.searchproject.pubmed.grpc.QueryResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SearchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SearchServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.searchproject.pubmed.grpc.SearchServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SearchService");
    }
  }

  private static final class SearchServiceFileDescriptorSupplier
      extends SearchServiceBaseDescriptorSupplier {
    SearchServiceFileDescriptorSupplier() {}
  }

  private static final class SearchServiceMethodDescriptorSupplier
      extends SearchServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SearchServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SearchServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SearchServiceFileDescriptorSupplier())
              .addMethod(getSearchQueryMethod())
              .addMethod(getSearchAidMethod())
              .build();
        }
      }
    }
    return result;
  }
}

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: SearchRequest.proto

package com.searchproject.pubmed.grpc;

public final class SearchRequestOuterClass {
  private SearchRequestOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_searchproject_pubmed_grpc_SearchRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_searchproject_pubmed_grpc_SearchRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023SearchRequest.proto\022\035com.searchproject" +
      ".pubmed.grpc\"!\n\rSearchRequest\022\020\n\010usernam" +
      "e\030\001 \001(\tB\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_com_searchproject_pubmed_grpc_SearchRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_searchproject_pubmed_grpc_SearchRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_searchproject_pubmed_grpc_SearchRequest_descriptor,
        new java.lang.String[] { "Username", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

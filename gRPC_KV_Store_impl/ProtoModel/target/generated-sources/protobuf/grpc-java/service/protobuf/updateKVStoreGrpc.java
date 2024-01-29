package service.protobuf;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.59.0)",
    comments = "Source: ProjectTwoProto.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class updateKVStoreGrpc {

  private updateKVStoreGrpc() {}

  public static final java.lang.String SERVICE_NAME = "service.protobuf.updateKVStore";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.protobuf.requestKVUpdate,
      service.protobuf.responseKVUpdate> getUpdateKVStoreMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "updateKVStore",
      requestType = service.protobuf.requestKVUpdate.class,
      responseType = service.protobuf.responseKVUpdate.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.protobuf.requestKVUpdate,
      service.protobuf.responseKVUpdate> getUpdateKVStoreMethod() {
    io.grpc.MethodDescriptor<service.protobuf.requestKVUpdate, service.protobuf.responseKVUpdate> getUpdateKVStoreMethod;
    if ((getUpdateKVStoreMethod = updateKVStoreGrpc.getUpdateKVStoreMethod) == null) {
      synchronized (updateKVStoreGrpc.class) {
        if ((getUpdateKVStoreMethod = updateKVStoreGrpc.getUpdateKVStoreMethod) == null) {
          updateKVStoreGrpc.getUpdateKVStoreMethod = getUpdateKVStoreMethod =
              io.grpc.MethodDescriptor.<service.protobuf.requestKVUpdate, service.protobuf.responseKVUpdate>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "updateKVStore"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.protobuf.requestKVUpdate.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.protobuf.responseKVUpdate.getDefaultInstance()))
              .setSchemaDescriptor(new updateKVStoreMethodDescriptorSupplier("updateKVStore"))
              .build();
        }
      }
    }
    return getUpdateKVStoreMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static updateKVStoreStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<updateKVStoreStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<updateKVStoreStub>() {
        @java.lang.Override
        public updateKVStoreStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new updateKVStoreStub(channel, callOptions);
        }
      };
    return updateKVStoreStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static updateKVStoreBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<updateKVStoreBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<updateKVStoreBlockingStub>() {
        @java.lang.Override
        public updateKVStoreBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new updateKVStoreBlockingStub(channel, callOptions);
        }
      };
    return updateKVStoreBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static updateKVStoreFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<updateKVStoreFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<updateKVStoreFutureStub>() {
        @java.lang.Override
        public updateKVStoreFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new updateKVStoreFutureStub(channel, callOptions);
        }
      };
    return updateKVStoreFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void updateKVStore(service.protobuf.requestKVUpdate request,
        io.grpc.stub.StreamObserver<service.protobuf.responseKVUpdate> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateKVStoreMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service updateKVStore.
   */
  public static abstract class updateKVStoreImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return updateKVStoreGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service updateKVStore.
   */
  public static final class updateKVStoreStub
      extends io.grpc.stub.AbstractAsyncStub<updateKVStoreStub> {
    private updateKVStoreStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected updateKVStoreStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new updateKVStoreStub(channel, callOptions);
    }

    /**
     */
    public void updateKVStore(service.protobuf.requestKVUpdate request,
        io.grpc.stub.StreamObserver<service.protobuf.responseKVUpdate> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateKVStoreMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service updateKVStore.
   */
  public static final class updateKVStoreBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<updateKVStoreBlockingStub> {
    private updateKVStoreBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected updateKVStoreBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new updateKVStoreBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.protobuf.responseKVUpdate updateKVStore(service.protobuf.requestKVUpdate request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateKVStoreMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service updateKVStore.
   */
  public static final class updateKVStoreFutureStub
      extends io.grpc.stub.AbstractFutureStub<updateKVStoreFutureStub> {
    private updateKVStoreFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected updateKVStoreFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new updateKVStoreFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.protobuf.responseKVUpdate> updateKVStore(
        service.protobuf.requestKVUpdate request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateKVStoreMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_UPDATE_KVSTORE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_UPDATE_KVSTORE:
          serviceImpl.updateKVStore((service.protobuf.requestKVUpdate) request,
              (io.grpc.stub.StreamObserver<service.protobuf.responseKVUpdate>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getUpdateKVStoreMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              service.protobuf.requestKVUpdate,
              service.protobuf.responseKVUpdate>(
                service, METHODID_UPDATE_KVSTORE)))
        .build();
  }

  private static abstract class updateKVStoreBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    updateKVStoreBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.protobuf.ProjectTwoProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("updateKVStore");
    }
  }

  private static final class updateKVStoreFileDescriptorSupplier
      extends updateKVStoreBaseDescriptorSupplier {
    updateKVStoreFileDescriptorSupplier() {}
  }

  private static final class updateKVStoreMethodDescriptorSupplier
      extends updateKVStoreBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    updateKVStoreMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (updateKVStoreGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new updateKVStoreFileDescriptorSupplier())
              .addMethod(getUpdateKVStoreMethod())
              .build();
        }
      }
    }
    return result;
  }
}

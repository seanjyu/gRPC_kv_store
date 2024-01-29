package projectTwo.KVStore;

import java.net.InetSocketAddress;

import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Grpc;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

/**
 * GrpcServerInterceptor class - impelements ServerInterceptor.
 */
public class GrpcServerInterceptor implements ServerInterceptor {
  public static final Context.Key<Object> REQUEST_ADDRESS = Context.key("requestAddress");
  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
          ServerCall<ReqT, RespT> serverCall,
          Metadata metadata,
          ServerCallHandler<ReqT, RespT> next) {

    // Use context to save Grpc address attributes
    if (serverCall.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR) != null) {
      final InetSocketAddress remoteAddress = (InetSocketAddress) serverCall.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR);
      final Context context = Context.current().withValue(REQUEST_ADDRESS, remoteAddress);
      return Contexts.interceptCall(context, serverCall, metadata, next);
    }
      return next.startCall(serverCall, metadata);
    }

  }

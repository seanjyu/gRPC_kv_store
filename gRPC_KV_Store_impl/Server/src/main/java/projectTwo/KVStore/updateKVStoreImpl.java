package projectTwo.KVStore;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

import io.grpc.stub.StreamObserver;
import service.protobuf.requestKVUpdate;
import service.protobuf.responseKVUpdate;
import service.protobuf.updateKVStoreGrpc;
/**
 *
 */
public class updateKVStoreImpl extends updateKVStoreGrpc.updateKVStoreImplBase{
  private static ConcurrentHashMap<String, String> concurrentHashMap;
  private static int port;
  private final ServerLogger serverLogger = ServerLogger.getInstance();
  public updateKVStoreImpl(ConcurrentHashMap<String, String> concurrentHashMap, int port) {
    updateKVStoreImpl.concurrentHashMap = concurrentHashMap;
    updateKVStoreImpl.port = port;
  }


  /**
   * updateKVStore - Method to handle KVStore service.
   * @param requestKVUpdate - Protobuf generated class to handle requests.
   * @param responseKVUpdateStreamObserver - Protobuf generated stream observer.
   */
  @Override
  public void updateKVStore(requestKVUpdate requestKVUpdate, StreamObserver<responseKVUpdate> responseKVUpdateStreamObserver) {
    try {
      // Access IP address using Grpc Interceptor.
      InetSocketAddress requestAddress = (InetSocketAddress) GrpcServerInterceptor.REQUEST_ADDRESS.get();

      // Log error if request Address is null
      if (requestAddress == null) {
        serverLogger.logError("Error with obtaining request address.");

      } else {

        //
        String status;
        status = serverUpdateKVStore(requestKVUpdate.getClientName(), requestKVUpdate.getMessage(),
                this.concurrentHashMap,
                this.serverLogger,
                requestAddress, port);

        responseKVUpdateStreamObserver.onNext(responseKVUpdate.newBuilder()
                .setUpdateStatus(status)
                .build());

        responseKVUpdateStreamObserver.onCompleted();
      }

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * serverUpdateKVStore - Method to update KVStore.
   * @param clientMessage - Message from client.
   * @param concurrentHashMap - Concurrent hash map to store values.
   * @param requestAddress - IPaddress of request.
   * @param port - Port number.
   * @return - Returns string representing the status of the KVStore.
   * @throws IOException - Serverlogger may throw IOexception.
   */
  private static synchronized String serverUpdateKVStore(String clientName, String clientMessage,
                                                         ConcurrentHashMap<String,
                                                                 String> concurrentHashMap,
                                                         ServerLogger serverLogger,
                                                         InetSocketAddress requestAddress,
                                                         int port) throws IOException {
    // Initialize variables and split input string
    if (clientMessage.equals("connectionCheck")) {
      serverLogger.logInfo("Connection with client made.");
      return "connectionSecure";
    }
    String[] splitParams = clientMessage.split(" ");
    InetAddress inetAddress = requestAddress.getAddress();

    // Create strings for storage, serverMsg or errorMsg will be changed based on client request
    String addressDataError = "\nClient Name: " + clientName +
            "\nIP address: " + inetAddress.toString().substring(1)
            + "\n" + "Port number: " + port + "\nParams Length: " + clientMessage.length()
            + "\nError message: ";
    String addressDataNormal ="\nClient Name: " + clientName +
            "\nIP address: " + inetAddress.toString().substring(1)
            + "\n" + "Port number: " + port + "\nParams Length: " + clientMessage.length()
            + "\nServer message: ";
    String serverMsg = addressDataNormal;
    String errorMsg = addressDataError;
    String status;

    // If statement for each possible case, note error message or server message will be added to
    // the log at the end.
    if (splitParams.length == 1) {
      errorMsg += "Invalid commands from client \n";
      status =  "8";
    } else if (splitParams.length > 3) {
      errorMsg += "Too many inputs from client\n";
      status = "7";
    } else if (splitParams[0].equalsIgnoreCase("put")) {
      try {
        concurrentHashMap.put(splitParams[1], splitParams[2]);
        serverMsg += "Successfully put entry to KVStore\n";
        status =  "1";
      } catch (Exception e) {
        errorMsg += "Error occured when inputting to KVStore\n";
        status = "4" + e;
      }
    } else if (splitParams[0].equalsIgnoreCase("get")) {
      try {
        if (concurrentHashMap.containsKey(splitParams[1])) {
          String getValue = concurrentHashMap.get(splitParams[1]);
          serverMsg += "Successfully got entry from KVStore\n";
          status =  "2" + getValue;
        } else {
          errorMsg += "Delete unsuccessful, key not in KVstore\n";
          status =  "5" + "Key not in KVstore";
        }
      } catch (Exception e) {
        errorMsg += "Error occured when getting from KVStore\n";
        status =  "5" + e;
      }

    } else if ((splitParams[0].equalsIgnoreCase("delete"))) {
      try {
        if (concurrentHashMap.containsKey(splitParams[1])) {
          concurrentHashMap.remove(splitParams[1]);
          serverMsg += "Successfully delete entry from KVStore\n";
          status = "3";
        } else {
          errorMsg += "Key not in KVstore\n";
          status = "6" + "Key not in KVstore\n";
        }
      } catch (Exception e) {
        errorMsg += "Error occured when removing from KVStore\n";
        status =  "6" + e;
      }
    } else {
      errorMsg += "Invalid commands from client \n";
      status =  "8";
    }

    // Add to message to log
    if (errorMsg.equals(addressDataError)) {
      serverLogger.logInfo(serverMsg);
    } else {
      serverLogger.logError(errorMsg);
    }

    // return status
    return status;
  }

  public static ConcurrentHashMap<String, String> getConcurrentHashMap() {
    return concurrentHashMap;
  }
}

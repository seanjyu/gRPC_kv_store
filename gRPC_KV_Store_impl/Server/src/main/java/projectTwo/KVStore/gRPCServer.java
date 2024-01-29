package projectTwo.KVStore;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import io.grpc.Server;

import static java.lang.System.currentTimeMillis;

import io.grpc.ServerInterceptors;
import io.grpc.netty.NettyServerBuilder;

public class gRPCServer {
  private static ServerLogger serverLogger;

  /**
   * Default constructor for GRPC Server
   */
  private gRPCServer() {
    this.serverLogger = serverLogger.getInstance();
  }
  public void runGRPCServer () {

    // Ask user for port
    Scanner serverScanner = new Scanner(System.in);
    Timestamp timeAskPort = new Timestamp(currentTimeMillis());
    System.out.println(timeAskPort + " :: Enter port number:");

    int port;
    while (true) {
      String checkPort = serverScanner.nextLine();
      // if want to exit from the start
      if (checkPort.equalsIgnoreCase("exit")) {
        System.exit(0);
      }
      if (checkPort.equals("0") || isNotInt(checkPort) || checkPort.split(" ").length > 1) {
        Timestamp timeInvalidPort = new Timestamp(currentTimeMillis());
        System.out.println(timeInvalidPort + " :: Invalid port, please enter a valid port");
      } else {
        port = Integer.parseInt(checkPort);
        Timestamp timeValidPort = new Timestamp(currentTimeMillis());
        System.out.println(timeValidPort + " :: Port passed integer check");
        break;
      }
    }

    ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
    updateKVStoreImpl service = new updateKVStoreImpl(concurrentHashMap, port);
    SocketAddress address = new InetSocketAddress("localhost", port);
    final Server server = NettyServerBuilder.forAddress(address)
            .addService(
              ServerInterceptors.intercept(
                    service, new GrpcServerInterceptor())).build();

    try {
      server.start();
      serverLogger.logInfo("Server started, awaiting RPC requests");

      while(!server.isTerminated()) {
        String serverInput = serverScanner.nextLine();
        if (serverInput.equalsIgnoreCase("print kvstore")) {
          ConcurrentHashMap<String, String> KVStorage = service.getConcurrentHashMap();
          if (KVStorage == null) {
            System.out.println("KVStore is null");
          } else {
            System.out.println("Current KV pairs in storage");
            KVStorage.forEach((key, value) -> System.out.println(key + ":" + value));
          }
        }
        if (serverInput.equalsIgnoreCase("exit")) {
          server.shutdown();

          serverLogger.logInfo("Server shutdown successful");
          serverLogger.closeLogFile();
          break;
        }

      }

      //System.exit(0);

    } catch (IOException e) {

      throw new RuntimeException(e);
    }
  }

  /**
   * Main method for GrpcServer.
   * @param args - null.
   */
  public static void main(String[] args) {
    gRPCServer server = new gRPCServer();
    server.runGRPCServer();
    System.exit(0);
  }

  /**
   * isNotInt - method to check if input is an integer.
   * @param input - String input.
   * @return boolean value.
   */
  private static boolean isNotInt(String input) {
    if (input == null || input.isEmpty()) {
      return true;
    }
    try {
      // Use parseInt method to check if input is integer
      int i = Integer.parseInt(input);
    } catch (NumberFormatException nfe) {
      return true;
    }
    return false;
  }
}

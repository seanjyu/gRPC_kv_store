import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.NettyChannelBuilder;
import service.protobuf.requestKVUpdate;
import service.protobuf.responseKVUpdate;
import service.protobuf.updateKVStoreGrpc;
import static java.lang.System.currentTimeMillis;

/**
 * Client class
 */
public class client {

  private ClientLogger clientLogger;

  /**
   * Default Constructor for client class.
   */
  public client () {
  }

  /**
   * Method to run client.
   */
  public void runClient() {
    // Read user inputs for host address/name and port number
    Scanner clientScanner = new Scanner(System.in);
    Timestamp timeStart = new Timestamp(currentTimeMillis());
    System.out.println(timeStart + ":: Please input a filename for the client logger, note if the" +
            " filename already exists there maybe mistakes in the generation of the client logger");
    Pattern specialCharacterChecker = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
    while (true) {
      String filename = clientScanner.nextLine();
      Matcher checkSpecialCharacter = specialCharacterChecker.matcher(filename);

      Timestamp timeIllegalFileName = new Timestamp(currentTimeMillis());
      // Check if file
      if (checkSpecialCharacter.find()) {
        System.out.println(timeIllegalFileName + ":: Please input a filename without special" +
                " characters");
      // Check if filename is too long
      } else if (filename.length() > 20) {
        System.out.println(timeIllegalFileName + ":: Please input a shorter filename");
      // Check for early exit
      } else if (filename.equalsIgnoreCase("exit")) {
        Timestamp earlyExit = new Timestamp(currentTimeMillis());
        System.out.println(earlyExit + ":: Client Exited");
        System.exit(0);
      } else {
        this.clientLogger = clientLogger.getInstance(filename);
        break;
      }
    }

    Timestamp timeClientName = new Timestamp(currentTimeMillis());
    System.out.println(timeClientName + ":: Please input a name for this client");
    String clientNameFinal;
    while (true) {
      String clientName = clientScanner.nextLine();
      Matcher checkSpecialCharacter = specialCharacterChecker.matcher(clientName);

      Timestamp timeIllegalFileName = new Timestamp(currentTimeMillis());
      // Check if file
      if (checkSpecialCharacter.find()) {
        System.out.println(timeIllegalFileName + ":: Please input a client name without special" +
                " characters");
        // Check if filename is too long
      } else if (clientName.length() > 20) {
        System.out.println(timeIllegalFileName + ":: Please input a shorter filename");
        // Check for early exit
      } else if (clientName.equalsIgnoreCase("exit")) {
        Timestamp earlyExit = new Timestamp(currentTimeMillis());
        System.out.println(earlyExit + ":: Client Exited");
        System.exit(0);
      } else {
        clientNameFinal = clientName;
        break;
      }
    }


      Timestamp timeAddress = new Timestamp(currentTimeMillis());
    System.out.println(timeAddress + " :: Please enter the Ip address followed by a space " +
            "followed by the port number:");
    String[] address = clientScanner.nextLine().split(" ");

    // Check for early exit
    if (address[0].equalsIgnoreCase("exit")) {
      Timestamp earlyExit = new Timestamp(currentTimeMillis());
      System.out.println(earlyExit + ":: Client Exited");
      System.exit(0);
    }

    // Check address input
    Timestamp timeCheckaddress = new Timestamp(currentTimeMillis());
    if (address.length != 2) {
      System.out.println(timeCheckaddress + " :: Please restart the client and input the host" +
              " address/name and port number with one space in between");
      System.exit(0);
    }

    // Check if port is an integer
    if (isNotInt(address[1]) || address[1].equals("0")) {
      System.out.println(timeCheckaddress + " :: Please restart the client and input a" +
              " valid port number");
      System.exit(0);
    }

    String clientIp = address[0];
    int clientPort = Integer.parseInt(address[1]);

    // Check for valid host address
    InetAddress inetAddress;
    try {
      inetAddress = InetAddress.getByName(clientIp);

    } catch (UnknownHostException e) {
      try {
        clientLogger.logError("Invalid host address entered\nCompiler Error message: "
                + e);
        System.out.println("Please start the client again and input a valid Host address/name and" +
                " port number");
        System.exit(0);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    }

    // Create channel to make remote procedure call
    final ManagedChannel channel  = NettyChannelBuilder
            .forAddress(clientIp, clientPort)
            .usePlaintext()
            .build();

    Timestamp timePrepopulate = new Timestamp(currentTimeMillis());
    System.out.println(timePrepopulate + " :: Address and port accepted");

    clientLogger.logInfo("Prepopulation Started");

    // 5 put commands
    String putStatus1 = requestReply(channel, "put key1 value1", clientNameFinal);
    parseMsgFromServer(putStatus1);
    String putStatus2 = requestReply(channel, "put key2 value2", clientNameFinal);
    parseMsgFromServer(putStatus2);
    String putStatus3 = requestReply(channel, "put key3 value3", clientNameFinal);
    parseMsgFromServer(putStatus3);
    String putStatus4 = requestReply(channel, "put key4 value4", clientNameFinal);
    parseMsgFromServer(putStatus4);
    String putStatus5 = requestReply(channel, "put key5 value5", clientNameFinal);
    parseMsgFromServer(putStatus5);

    // 5 get commands
    String getStatus1 = requestReply(channel, "get key1 value1", clientNameFinal);
    parseMsgFromServer(getStatus1);
    String getStatus2 = requestReply(channel, "get key2 value2", clientNameFinal);
    parseMsgFromServer(getStatus2);
    String getStatus3 = requestReply(channel, "get key3 value3", clientNameFinal);
    parseMsgFromServer(getStatus3);
    String getStatus4 = requestReply(channel, "get key4 value4", clientNameFinal);
    parseMsgFromServer(getStatus4);
    String getStatus5 = requestReply(channel, "get key5 value5", clientNameFinal);
    parseMsgFromServer(getStatus5);

    // 5 delete commands
    String deleteStatus1 = requestReply(channel, "delete key1 value1", clientNameFinal);
    parseMsgFromServer(deleteStatus1);
    String deleteStatus2 = requestReply(channel, "delete key2 value2", clientNameFinal);
    parseMsgFromServer(deleteStatus2);
    String deleteStatus3 = requestReply(channel, "delete key3 value3", clientNameFinal);
    parseMsgFromServer(deleteStatus3);
    String deleteStatus4 = requestReply(channel, "delete key4 value4", clientNameFinal);
    parseMsgFromServer(deleteStatus4);
    String deleteStatus5 = requestReply(channel, "delete key5 value5", clientNameFinal);
    parseMsgFromServer(deleteStatus5);

    clientLogger.logInfo("Prepopulation Completed");
    // Allow
    Timestamp timeAwaitRPC = new Timestamp(currentTimeMillis());
    System.out.println(timeAwaitRPC + " :: Please input commands," +
            " for help on commands see readMe");

    Scanner scanner = new Scanner(System.in);

    // send RPC requests to server
    while (true) {
      String msgToSend = scanner.nextLine();
      if (msgToSend.equalsIgnoreCase("exit")) break;
      try {
        final responseKVUpdate serverResponse = newBlockingStub(channel)
                .updateKVStore(requestKVUpdate.newBuilder().setClientName(clientNameFinal)
                        .setMessage(msgToSend).build());
        parseMsgFromServer(serverResponse.getUpdateStatus());

      } catch (StatusRuntimeException e) {
        try {
          ClientLogger.logError("Unable to send request to server," +
                  " make sure the server is running. The client will now close.");
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      }
    }

    ClientLogger.logInfo("Client exit Successful");
    this.clientLogger.closeLogFile();

  }

  /**
   * Main method - creates
   * @param args - null.
   */
  public static void main(String[] args) {
    client client = new client();
    client.runClient();
    System.exit(0);
  }

  /**
   * Method to create server request and return server response.
   * @param channel - channel to send messages to server.
   * @param msgToSend - String of message to send.
   * @param clientName - Name of client for server refernce.
   * @return - update status of server.
   */
  private static String requestReply (ManagedChannel channel, String msgToSend, String clientName) {
    final responseKVUpdate serverResponse = newBlockingStub(channel)
            .updateKVStore(requestKVUpdate.newBuilder().setClientName(clientName)
                    .setMessage(msgToSend).build());
    return serverResponse.getUpdateStatus();
  }

  /**
   * newBlockgStub - Creates a blockingStub to send requests and receive requests to server.
   * @param channel - channel to send message.
   * @return - protobuf generated class with request messages.
   * @throws StatusRuntimeException - If
   */
  private static updateKVStoreGrpc.updateKVStoreBlockingStub newBlockingStub(
          final ManagedChannel channel) throws StatusRuntimeException {
    return updateKVStoreGrpc.newBlockingStub(channel).withDeadlineAfter(
            3000, TimeUnit.MILLISECONDS);
  }

  /**
   * isNotInt - Method to check whether string input is an integer.
   * @param input - input String.
   * @return - Boolean if input is an integer.
   */
  public boolean isNotInt(String input) {
    if (input == null || input.isEmpty()) {
      return true;
    }
    try {
      int i = Integer.parseInt(input);
    } catch (NumberFormatException nfe) {
      return true;
    }
    return false;
  }

  /**
   * parseMsgFromServer - Method to parse method from server.
   * @param msgFromServer - String msg from server relaying result of client message.
   * @return Status represented by string.
   */
  public static String parseMsgFromServer(String msgFromServer) {
    String msgToUser = "";
    String errorMsg = "";
    // Exit case
    if (msgFromServer.equals("1")) {
      msgToUser = "Successfully put entry to KVStore";
    } else if (msgFromServer.equalsIgnoreCase("8")) {
      errorMsg = "Server received invalid commands, please see documentation";
    } else if (msgFromServer.substring(0, 1).equalsIgnoreCase("2")) {
      msgToUser = "Successfully get value from KVStore";
      msgToUser += "\ngetValue: " + msgFromServer.substring(1) + ".";
    } else if (msgFromServer.substring(0, 1).equalsIgnoreCase("3")) {
      msgToUser = "Successfully delete key value form KVStore";
    } else if (msgFromServer.substring(0, 1).equalsIgnoreCase("4")) {
      errorMsg = msgFromServer.substring(1);
    } else if (msgFromServer.substring(0, 1).equalsIgnoreCase("5")) {
      errorMsg = msgFromServer.substring(1);
    } else if (msgFromServer.substring(0, 1).equalsIgnoreCase("6")) {
      errorMsg = msgFromServer.substring(1);
    } else if (msgFromServer.equalsIgnoreCase("7")) {
      errorMsg = "Server received too many inputs, please see documentation";
    }
    if (errorMsg.isEmpty()) {
      ClientLogger.logInfo(msgToUser);
    } else if (msgToUser.equals("Successfully get value from KVStore")) {
      return msgFromServer.substring(1);
    } else {
      try {
        ClientLogger.logError(errorMsg);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return "1";
  }
}

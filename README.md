# RPC implementation ReadMe

## Executing Code
### Using Jar files
Jar files are located in the directory 
`gRPC_KV_Store_impl/out/artifacts`
There are two different folders and jar files namely
`server` will run the Server
Unfortunately due to a dependency the jar file for the client was unable to be made. Please use the individual java file to start the client.

### Using Individual Files
The file for the server is located in: `gRPC_KV_Store_impl/Server/src/main/java/projectTwo/KVStore/GRPCServer.java`

The file for the client is located in: 
`gRPC_KV_Store_impl/Client/src/main/java/client.java`

Note that executing the client files when the server is not running will result in the clients not being able to connect.

Also note, in Intellij multiple instances of the same application can be run simultaneously.
In the top right corner edit configurations -> add new configuration -> application -> Rename application -> select client module -> select client main class -> modify options (on the right middle) -> allow multiple instances -> apply.
### Commands

##### Server
The instructions for the server are similar to the first project.

After the file is executed the user will be prompted to provide a port number. There are several checks in place to make sure the port number is valid.
Example inputs would be:
`12345`
`8888`
`010101`

If an acceptable port is inputted the server will be started an will wait for RPC requests.

To exit the server simply type `exit` and the program should stop.

Note the server address is automatically set to localhost however it can be changed in the code. 
The file location is: 
`gRPC_KV_Store_impl/Server/src/main/java/projectTwo/KVStore/GRPCServer.java`
The line number is 54 and it is copied below:
`SocketAddress address = new InetSocketAddress("localhost", port);`
To change the address simply change `"localhost"` to the appropriate IP.
##### Clients
The client has several starting inputs.

The first input is the filename for the logger. The filename cannot have special characters or be longer than 20 characters. Also the file extension is not required.
Example inputs would be: 
`clientlogger1
`clinetlogger2`

Then the user will be prompted for a client name. The same rules of the filename apply to the client name. The client name is used to identify calls to the server.
Example inputs would be:
`'client1'`
`some client`

Then the user will be prompted to give an address and an port number with a single space inbetween.
Example inputs would be
`localhost 12345`
`127.0.0.1 8888`

Note if an invalid address or port number is supplied then the user will be prompted to execute the file again.

If all the above are inputted correctly, commands can be performed to edit the KVStore.
##### Server Commands
For convenience the following command can be input in the terminal of the server to print the current KVStore
`print kvstore`
##### Client Commands
After the client is connected to the server the user can input 3 commands. For all the commands the parameters are split by a single space.
`put` - Put command to add values to the KVStore
	Example:
		`put key1 value1`
`get` - Get command to get values from the KVStore
	Example:
		`get key1`
`delete` - Delete command to delete values from the KVStore
	Examples:
		`delete key1`

More examples can be found in the Prepopulate sections in the client code. The location is as follows:
file location: `gRPC_KV_Store_impl/Client/src/main/java/client.java`
lines 149-171
### Logs
The logs for the client and server are located in `gRPC_KV_Store_impl` folder. The filename for the client logger is given by the user and the are server logger file is called `ServerLogger.txt`. Note all the logs also show up in the console of the respective programs.

## Additional Documentation
### General Design Flow - Explanation for each file
##### Server
The server (`Server/src/main/java/projectTwo/KVStore/gRPCServer.java`) contains the method calls that starts the server. The server instantiates the concurrent hash map and uses the implementation of the method call created by the protocol buffer to handle reply request calls. The actual implementation can be found in the file `updateKVStoreImpl.java`. This file also contains the synchronized method which is used to edit the key value storage. Note, in this folder the `serverLogger.java` file contains the server logging methods.
##### Client
The client (`Client/src/main/java/client.java`) uses the gRPC.Netty channelbuilder and uses the objects created through the protocol buffer to send messages to the server. The client will receive a response status corresponding to whether the action on the KVStore was performed properly. The method to parse the status is located in the same file and is called. Note, similar to the server, the client logger implementation is also found in the same folder.

### Error Handling
The following is a list of errors that were considered for the project.
##### Server
The server checks for whether the port input is a number. And there are also several check on the validity of the user inputs (whether they match the required syntax).
##### Status Numbers
Test are done on the user inputs, the results of these check correspond to various status numbers. These check are done in the `AbstractServer` file (`serverUpdateKVStore` method). These numbers are then transmitted back to the client and the appropriate messages are given to the client. Below is a table for each of the status numbers.
- 1 - Successfully put entry to KVStore
- 2 - Successfully got entry from KVStore
- 3 - Successfully delete entry from KVStore
- 4 - Error occurred when inputting to KVStore
- 5 - Error occurred when getting from KVStore, this includes invalid invalid key
- 6 - Error occurred when deleting from KVStore, this includes invalid input key
- 7 - Too many input commands
- 8 - Invalid or unrecognized command

##### Client
The gRPC Blocking stub is used to check for timeouts, the timeout time was set to 3 seconds (see line 252 of client file). The UnknownHostException is used to test for an invalid address. Checks are also done on the input port number similar to the server. 

There is no specific failure checking to see if the server is on, other than if a RPC request is sent and not properly received a request timeout will occur and the client will shutdown.
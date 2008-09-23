package Lab1.server;

import java.net.*;
import java.io.*;

public class HMServer {
	public static void main (String args[]) {
		try{
			int serverPort = 4504; // the server port
			ServerSocket FrontDesk = new ServerSocket(serverPort);

			// We need only one ServerSocket : FrontDesk is an open generic socket
			// listening for request for connection from client processes.


			while(true) {

				// a client requests to connect to server
				System.out.println("Waiting for request to connect ");
				Socket ConnectionToOneClient = FrontDesk.accept(); 
				System.out.println("Connect accepted");
				// Accept the connect, a virtual channel is now set between the client and the server

				DataInputStream in;		// this stream is to receive a String from client
				DataOutputStream out;	// this stream is to send to a String to client

				// bound the streams to the connect to the client
				in = new DataInputStream( ConnectionToOneClient.getInputStream());
				out =new DataOutputStream( ConnectionToOneClient.getOutputStream());
				System.out.println("Waiting for incoming message");					    
				String messageFromClient = in.readUTF(); 	// read a line of data from the stream
				System.out.println("message received");											
				// UTF = Universal Text Format

				String messageToClient = "TESTING";
				out.writeUTF(messageToClient);

				//       ConnectionToOneClient.close();	//End of the conversation close the connection to the client
			}
		} catch(Exception e) {
			System.out.println("Exception caught in Server : "+e.getMessage());
		}
	}
}

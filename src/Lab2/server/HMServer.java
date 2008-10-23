package Lab2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import server.words;

public class HMServer {

	private void listen()throws Exception{


		int serverPort = 4504; // the server port
		ServerSocket FrontDesk = new ServerSocket(serverPort);
		// We need only one ServerSocket : FrontDesk is an open generic socket
		// listening for request for connection from client processes.

		WordsMonitor wordsMonitor=new WordsMonitor();


		while(true) {

			// a client requests to connect to server
			System.out.println("Waiting for request to connect ");
			Socket ConnectionToOneClient = FrontDesk.accept(); 
			System.out.println("Connect accepted");
			// Accept the connect, a virtual channel is now set between the client and the server

			Thread connection=new Thread( new ConnectionHandler(ConnectionToOneClient, wordsMonitor));
			connection.start();

			//ConnectionToOneClient.close();	//End of the conversation close the connection to the client
		}

	}

	public static void main (String args[]) throws Exception{
		
		//start server and listen for connections from clients
		HMServer server=new HMServer();
		server.listen();

	}
}
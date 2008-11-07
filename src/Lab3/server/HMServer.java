package Lab3.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import Lab2.Interface.scoreNotification;


import server.words;

public class HMServer{

	ScoreMonitor scoreMonitor=new ScoreMonitor();
	WordsMonitor wordsMonitor=new WordsMonitor();
	
	private void listen()throws Exception {

		
        // start the registry on port 2010
        Registry reg = LocateRegistry.createRegistry(2010);            
        //Create the object that will be invoked
        ScoreUpdater obj = new ScoreUpdater(scoreMonitor);
        //link it to its interface
        scoreNotification objectInterface = (scoreNotification) UnicastRemoteObject.exportObject(obj,0);
        // bind the object to the registry so that it can be invoked remotely
        reg.bind("myObjectName",objectInterface);
		
        
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
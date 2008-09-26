package Lab1.server;

import java.net.*;
import java.io.*;
import server.words;

public class ConnectionHandler implements Runnable{

	Socket ConnectionToOneClient;

	DataInputStream in;		// this stream is to receive a String from client
	DataOutputStream out;	// this stream is to send to a String to client

	WordsMonitor wordsMonitor;
	int round=0;

	public ConnectionHandler(Socket ConnectionToOneClient,WordsMonitor wordsMonitor)
	throws Exception{

		this.ConnectionToOneClient=ConnectionToOneClient;
		// bound the streams to the connect to the client
		in = new DataInputStream( ConnectionToOneClient.getInputStream());
		out =new DataOutputStream( ConnectionToOneClient.getOutputStream());


		this.wordsMonitor=wordsMonitor;
	}

	public void run(){

		System.out.println("Waiting for round number...");		


		try{
			round = in.readInt();
			System.out.println("Received Round Number" + round);
		}catch(IOException io){
			io.printStackTrace(); 
			System.exit(1); 
		}

		try{
			String messageToClient = wordsMonitor.getNext(round);
			System.out.println("The next word sent to client: " +  messageToClient);
			out.writeUTF(messageToClient);


		}catch(IOException io2){
			io2.printStackTrace(); 
			System.exit(1); 
		}
	}


}


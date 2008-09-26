package Lab1.server;

import java.net.*;
import java.io.*;
import server.words;

public class ConnectionHandler implements Runnable{

	Socket ConnectionToOneClient;
	
	DataInputStream in;		// this stream is to receive a String from client
	DataOutputStream out;	// this stream is to send to a String to client
	
	WordsMonitor wordsMonitor;
	int round;
	
	public ConnectionHandler(Socket ConnectionToOneClient,WordsMonitor wordsMonitor)
	throws Exception{
		
		this.ConnectionToOneClient=ConnectionToOneClient;
		// bound the streams to the connect to the client
		in = new DataInputStream( ConnectionToOneClient.getInputStream());
		out =new DataOutputStream( ConnectionToOneClient.getOutputStream());
		
		
		this.wordsMonitor=wordsMonitor;
		round=0;
	}
	
	public void run(){



		System.out.println("Waiting for incoming message");		
		while(true){
			try{
				String messageFromClient = in.readUTF(); 	// read a line of data from the stream
				// UTF = Universal Text Format
				System.out.println("message received");	
			}catch(IOException io){
				io.printStackTrace(); 
				System.exit(1); 
			}

			try{
				String messageToClient = wordsMonitor.getNext(round);
				round++;
				out.writeUTF(messageToClient);

			}catch(IOException io2){
				io2.printStackTrace(); 
				System.exit(1); 
			}
		}
	
	}
}


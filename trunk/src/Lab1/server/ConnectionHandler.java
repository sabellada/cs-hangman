package Lab1.server;

import java.net.*;
import java.io.*;
import server.words;

public class ConnectionHandler implements Runnable{

	Socket ConnectionToOneClient;
	
	DataInputStream in;		// this stream is to receive a String from client
	DataOutputStream out;	// this stream is to send to a String to client
	
	words myWord;
	
	public ConnectionHandler(Socket ConnectionToOneClient,words myWord)throws IOException{
		this.ConnectionToOneClient=ConnectionToOneClient;
		// bound the streams to the connect to the client
		in = new DataInputStream( ConnectionToOneClient.getInputStream());
		out =new DataOutputStream( ConnectionToOneClient.getOutputStream());
		
		
		this.myWord=myWord;
	}
	
	public void run(){



		System.out.println("Waiting for incoming message");		
		try{
			String messageFromClient = in.readUTF(); 	// read a line of data from the stream
			// UTF = Universal Text Format
			System.out.println("message received");	
		}catch(IOException io){
      		io.printStackTrace(); 
         	System.exit(1); 
		}
		
		try{
			String messageToClient = myWord.next();
			out.writeUTF(messageToClient);
		}catch(IOException io2){
      		io2.printStackTrace(); 
         	System.exit(1); 
		}
	
	}
}


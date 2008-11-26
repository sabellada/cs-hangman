/*
 * gui.java
 *
 * Created on September 3, 2008, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Lab4b.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Lab2.Interface.scoreNotification;
import Lab3.server.ConnectionHandler;


import gui.gameBoard;
/**
 *
 * @author Joe Oommen and Riley Onabigon
 */
public class gui extends gameBoard{
	// this class must extend gameBoard
	private int round = 0;
	int serverPort;
	boolean serverUp;
	boolean isBackup;
	
	/** Creates a new instance of gui */
	public gui(/* declare any argument you need*/) {
		// initialize any variable you need



		// init is a method implemented in gameBoard class and it sets the layout
		// in the interface. You need to call it only once.
		init();
		
		serverPort = 4504;
		serverUp=true;
		isBackup=false;
		Thread scoreReader=new Thread( new ScoreReader(this));
		scoreReader.start();
		
		Thread serverFailureDeterter=new Thread( new ServerFailureDetecter(this));
		serverFailureDeterter.start();
		
	}
	
	

	@Override
	public void resetGameBoard() {
			
		if(serverUp){
			try{
				
				/*----------------------RPC to send scores-------------------------*/
				Registry registry = LocateRegistry.getRegistry("localhost",2010);
				// create the object which is only the INTERFACE
				// Note that the object is identified by its name 'myObjectName'
				// which the name used by the server when binding it to the registry
				scoreNotification obj = (scoreNotification)registry.lookup("myObjectName");
				
				obj.notify(getPlayerName(), getCurrentScore());
				/*----------------------END of RPC---------------------------------*/
			}catch (Exception e) {}
		}	
			// clean up the previous game;
			cleanUp();      
			
			/*---------------------Connect to server----------------------*/
			// set the new word
			// BEGINING OF REQUIRED MODIFICATION ******************************
			// contact the server in order to get the word to guess then assign
			// the new word to the variable 'theWord';
		if(serverUp){
			try{
				System.out.println("Request to connect to server");
				Socket s = new Socket("localhost", serverPort); 
				System.out.println("Connection set");
				// If the server process is not on localhost, for instance on 508AA-PC10,
				// while the client is on 508AA-PC-09, then we would have 
				// Socket s = new Socket("508AA-PC10", serverPort);  
	
				// When creating this Socket, the system tries to set a connection
				// to the server. After the server executes the method "accept", the connection is set.
	
	
				// Bind the in/out streams to the socket
				DataInputStream in = new DataInputStream( s.getInputStream());
				DataOutputStream out =new DataOutputStream( s.getOutputStream());
	
	
				out.writeInt(round);
	
				String wordFromServer = in.readUTF(); 
				theWord=wordFromServer;
	
				round++;
				System.out.println("Received: "+ wordFromServer) ; 
				System.out.println("Current Round Number: "+ round) ;
				/*-----------------------END connect to server--------------------*/
	
			}catch (Exception e) {
					//set that the server is down;
					serverUp=false;
					serverDown();	
			}
		}else if(!serverUp&isBackup){
			updateDebugArea("Cannot continue until main server is restored");
		}else{
			
		}

		// END OF REQUIRED MODIFICATION ***********************************

		setNewWord();   // display the new game
	}
	
	public void serverDown() {
		
		
		updateDebugArea("Unable to connect to server, notifying other clients");
		multicastRound();


		//assume that you are the new leader until a
		//finding a game with a higher round
	    
		
	}
	
	public void findLeader(DatagramPacket messageIn){
		updateDebugArea("Server Down");
		String round=new String(messageIn.getData());
		round=round.trim();
		int recievedRound=round.hashCode()-48;
		updateDebugArea("Recieved "+round.trim());
		
		//int start=pageContent.indexOf("<response>")+10;
		//int end=pageContent.indexOf("</response>");
		//return pageContent.substring(start, end); 

		
	}
	
	public void multicastRound(){
		//send round to all toehr clients
		try{
			MulticastSocket MulticastChannel =new MulticastSocket();
	
	        InetAddress group = InetAddress.getByName("228.5.6.8");  
	        // must use a multicast address

	        
	        // When sending only a multicast message, joining a multicast group is not required
	        //MulticastChannel.joinGroup(group);  
	
	        // multicast is based on UDP ... so same datagram structure
	        String round=new String(this.round+"");
	        byte [] message = round.getBytes();
	        // Note that the message MUST contain the port on which the receivers
	        // are listening.
	        DatagramPacket messageToSend = new DatagramPacket(message, message.length, group, 6501);
	        MulticastChannel.send(messageToSend);	
	
	        // if we did not join the group, then we must not call 'leaveGroup'
	        //MulticastChannel.leaveGroup(group);  
	
	        MulticastChannel.close();        		      	
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	}




}
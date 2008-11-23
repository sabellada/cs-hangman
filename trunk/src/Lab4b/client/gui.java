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
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
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
	
	/** Creates a new instance of gui */
	public gui(/* declare any argument you need*/) {
		// initialize any variable you need



		// init is a method implemented in gameBoard class and it sets the layout
		// in the interface. You need to call it only once.
		init();
		
		serverPort = 4504;
		
		Thread scoreReader=new Thread( new ScoreReader(this));
		scoreReader.start();
		
		Thread serverFailureDeterter=new Thread( new ServerFailureDetecter(this));
		serverFailureDeterter.start();
		
	}
	
	

	@Override
	public void resetGameBoard() {
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
			
			// clean up the previous game;
			cleanUp();      
			
			/*---------------------Connect to server----------------------*/
			// set the new word
			// BEGINING OF REQUIRED MODIFICATION ******************************
			// contact the server in order to get the word to guess then assign
			// the new word to the variable 'theWord';

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
			
			serverDown();
			
			//Multicast that server has failed
			//send round number+port number etc
			//get all other processes round+port etc
			//figure out which process is the new server
			
			//if this process is the new server
			//scan for server restarting
			//if server has restarted
			//
			//disable new words
			
			//else if other process is new server
			//notify new process 
			//switch this processes server info to match the new server
		}

		// END OF REQUIRED MODIFICATION ***********************************

		setNewWord();   // display the new game
	}
	
	public void serverDown(){
		
		System.out.println("Unable to connect to server, notifying other clients");


		//send round to all toehr clients
		try{
			MulticastSocket MulticastChannel =null;
	
	        InetAddress group = InetAddress.getByName("228.5.6.8");  
	        // must use a multicast address
	        
	        // create a socket on any available port ... we are not receiving
	        MulticastChannel = new MulticastSocket();
	        
	        // When sending only a multicast message, joining a multicast group is not required
	        //MulticastChannel.joinGroup(group);  
	
	        // multicast is based on UDP ... so same datagram structure
	        String round=new String(this.round+"");
	        byte [] message = round.getBytes();
	        // Note that the message MUST contain the port on which the receivers
	        // are listening.
	        DatagramPacket messageToSend = new DatagramPacket(message, message.length, group, 6500);
	        MulticastChannel.send(messageToSend);	
	
	        // if we did not join the group, then we must not call 'leaveGroup'
	        //MulticastChannel.leaveGroup(group);  
	
	        MulticastChannel.close();        		      	
	    }catch(Exception e){
	        e.printStackTrace();
	    }
	    
		//assume that you are the new leader until a
		//finding a game with a higher round
	    
		
	}
	
	public void findLeader(){
		System.out.println("Server Down, finding backup");

		
	}

}

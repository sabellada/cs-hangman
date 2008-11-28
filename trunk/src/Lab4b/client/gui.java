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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Lab4b.Interface.scoreNotification;


import gui.gameBoard;
/**
 *
 * @author Joe Oommen and Riley Onabigon
 */
public class gui extends gameBoard{
	// this class must extend gameBoard
	private int round = 0;
	int highestRound;
	int serverPort;
	InetAddress serverAddress;
	boolean serverUp;
	boolean isBackup;
	boolean atMaxRound;
	boolean first;
	BackupWordsMonitor words; 
	/** Creates a new instance of gui */
	public gui(/* declare any argument you need*/) {
		// initialize any variable you need



		// init is a method implemented in gameBoard class and it sets the layout
		// in the interface. You need to call it only once.
		init();
		
		serverPort = 4504;
		serverUp=true;
		isBackup=false;
		atMaxRound=false;
		first=false;
		
		words=new BackupWordsMonitor();
		
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
		if(serverUp||(round<highestRound)){
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
				words.addWord(theWord);
				
				round++;
				System.out.println("Received: "+ wordFromServer) ; 
				System.out.println("Current Round Number: "+ round) ;
				/*-----------------------END connect to server--------------------*/
	
				setNewWord();   // display the new game
			}catch (Exception e) {
					//set that the server is down;
					serverDown();	
			}
		}

		// END OF REQUIRED MODIFICATION ***********************************

	}
	
	public void serverDown() {
		
		updateDebugArea("\n Unable to connect to server, notifying all clients\n");
		highestRound=round;
		first=true;
		try{
			ServerSocket backupSocket = new ServerSocket(0);
			multicastRound(backupSocket);
			
			Thread backupServer=new Thread( new BackupServer(this, backupSocket, words));
			backupServer.start();

		}catch(Exception e){} 
		
		Thread serverRestorer=new Thread( new ServerRestorer());
		serverRestorer.start();
	    
		
	}
	
	public void findLeader(DatagramPacket messageIn){
		if(highestRound==0)highestRound=round;
		updateDebugArea("\n Server Down\n");
		serverUp=false;
		
		String message=new String(messageIn.getData());
		message=message.trim();

		//get round and port number of client claiming poistion of new server	
		int firstSpace=message.indexOf(" ");
		Integer messageRound=new Integer(message.substring(0,firstSpace).trim());
		Integer messagePort=new Integer(message.substring(firstSpace+1).trim());
		updateDebugArea("Recieved "+messageRound.toString()+" from "+messagePort.toString()+messageIn.getAddress()+"\n");
		
		//if other client does have a higher round keep it as the server
		if(messageRound.intValue()>highestRound){
									
			highestRound=messageRound.intValue();
			serverPort=messagePort.intValue();
			serverAddress=messageIn.getAddress();
			
			if(messagePort.intValue()==4504){
				serverUp=true;
				if(highestRound==round){
				highestRound=0;
				resetGameBoard();
				}
				
			}
			
			
			if(first){
				first=false;
				resetGameBoard();
			}
			
		//if equal cannot continue anyways so wait for main server to come back
		}else if(messageRound.intValue()<round){
			try {
				
				//create new datagram socket
				ServerSocket backupSocket = new ServerSocket(0);

                
                //multicast that you are the new server
                multicastRound(backupSocket);
                /*--------------------------START NEW SERVER BACKUP THREAD------------------*/
    			Thread backupServer=new Thread( new BackupServer(this, backupSocket, words));
    			backupServer.start();
    			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}//else do nothing since we already have a server with a higher round
		
	}
	
	public void multicastRound(ServerSocket backupSocket){
		//send round to all other clients
		try{
			MulticastSocket MulticastChannel =new MulticastSocket();
	
	        InetAddress group = InetAddress.getByName("228.5.6.8");  
	        // must use a multicast address

	        
	        // When sending only a multicast message, joining a multicast group is not required
	        //MulticastChannel.joinGroup(group);  
	
	        // multicast is based on UDP ... so same datagram structure
	        String round=new String(this.round+" "+backupSocket.getLocalPort());
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
	
	public void stopBackup(){
		
	}




}

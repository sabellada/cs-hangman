package Lab4b.server;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import server.scoreRecords;
import server.words;
/*
 * ScoreMonitor is used to keep the scoreRecords thread safe
 */
public class ScoreMonitor {	

	//keeps track of all scores
	scoreRecords scores=new scoreRecords();

	/*
	 * updates the list of scores
	 * @ player the name of the player sending the score
	 * @score the player's score
	 */
	public synchronized void addScore(String player, int score){
		scores.addRecord(player,score);
	}
	
	
	
	/*
	 * returns the score of the round to the client
	 */
	public synchronized void sendScores(){
			try{
				MulticastSocket MulticastChannel =null;
		
		        InetAddress group = InetAddress.getByName("228.5.6.7");  
		        // must use a multicast address
		        
		        // create a socket on any available port ... we are not receiving
		        MulticastChannel = new MulticastSocket();
		        
		        // When sending only a multicast message, joining a multicast group is not required
		        //MulticastChannel.joinGroup(group);  
		
		        // multicast is based on UDP ... so same datagram structure
		        byte [] message = scores.getAllScores().getBytes();
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
	}

}

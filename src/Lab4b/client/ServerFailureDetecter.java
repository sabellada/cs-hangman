package Lab4b.client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServerFailureDetecter implements Runnable{
	
	gui myGameBoard;
	
	public ServerFailureDetecter(gui myGameBoard){
		this.myGameBoard=myGameBoard;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		try{
            MulticastSocket multicastChannel =null;

            // A group is identified by its multicast address
            InetAddress group = InetAddress.getByName("228.5.6.8");
            // create a socket on the specified port
            multicastChannel = new MulticastSocket(6501);
            // 'map' the socket to the multicast group
            multicastChannel.joinGroup(group);  
            // in order to receive multicast message, the receiver must join the multicast group
            // it is similar to joining a mailing-list in order to receive messages posted to that group
            

            
            for(;;){            
            	      	           	
		            byte[] messageReceived = new byte[32];
		            DatagramPacket messageIn = new DatagramPacket(messageReceived, messageReceived.length);
		            multicastChannel.receive(messageIn);
		            myGameBoard.findLeader(messageIn);
		            

		            
		            
		            //End timeout
		            //if this process is the leader
		            //go through rounds to see who's leader
		            //mygameboard.isLeader
		            //else 
		            //    
            	}
		            
            
		}catch(Exception e){
            	e.printStackTrace();
            }
	}



}

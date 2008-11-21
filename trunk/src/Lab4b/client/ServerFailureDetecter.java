package Lab4b.client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServerFailureDetecter implements Runnable{
	
	gui myGameBoard;
	DatagramPacket messageIn;
	InetAddress group;
	
	public ServerFailureDetecter(gui myGameBoard){
		this.myGameBoard=myGameBoard;
	}
	
	
	public void run() {
		// TODO Auto-generated method stub
		try{
            MulticastSocket multicastChannel =null;

            // A group is identified by its multicast address
            group = InetAddress.getByName("228.5.6.7");
            // create a socket on the specified port
            multicastChannel = new MulticastSocket(6501);
            // 'map' the socket to the multicast group
            multicastChannel.joinGroup(group);  
            // in order to receive multicast message, the receiver must join the multicast group
            // it is similar to joining a mailing-list in order to receive messages posted to that group
            

            
            for(;;){            
            	
            	//if mygameboard.serverFailed
            	if(myGameBoard.serverDown()){
            		byte[] message=new byte[32];
            		//message=new String(myGameBoard.getRound());
    		        DatagramPacket messageToSend = new DatagramPacket(message, message.length, group, 6501);
    		        multicastChannel.send(messageToSend);	
            	}else{            	
            	
		            byte[] messageReceived = new byte[32];
		            messageIn = new DatagramPacket(messageReceived, messageReceived.length);
		            
		            //PUT TIMEOUT!!!
		            
		            multicastChannel.receive(messageIn);
		            
		            findLeader();
		            
		            //End timeout
		            //if this process is the leader
		            //go through rounds to see who's leader
		            //mygameboard.isLeader
		            //else 
		            //    
            	}
		            
            } 
            
		}catch(Exception e){
            	e.printStackTrace();
            }
	}


	private void findLeader() {

		
	}
}

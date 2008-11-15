package Lab4b.client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
//This class implements Runnable to check for the scores

public class ScoreReader implements Runnable {

	gui myGameBoard;
	
	public ScoreReader(gui myGameBoard){
		this.myGameBoard=myGameBoard;
	}
	@Override
	public void run() {
        try{
            MulticastSocket multicastChannel =null;

            // A group is identified by its multicast address
            InetAddress group = InetAddress.getByName("228.5.6.7");
            // create a socket on the specified port
            multicastChannel = new MulticastSocket(6500);
            // 'map' the socket to the multicast group
            multicastChannel.joinGroup(group);  
            // in order to receive multicast message, the receiver must join the multicast group
            // it is similar to joining a mailing-list in order to receive messages posted to that group
            for(;;){
	            byte[] messageReceived = new byte[1024];
	            DatagramPacket messageIn = new DatagramPacket(messageReceived, messageReceived.length);
	            multicastChannel.receive(messageIn);
	            
	            
	            myGameBoard.updateAllPlayersScore((new String(messageIn.getData())).trim()) ;
            }
           //System.out.println("Received:" + new String(messageIn.getData()));

           // multicastChannel.leaveGroup(group);	
           // leave the group, will no longer message destined to it	
           //multicastChannel.close();
        }catch(Exception e){
            e.printStackTrace();
        }
		
	}

	
	
}

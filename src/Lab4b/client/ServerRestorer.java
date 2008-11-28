package Lab4b.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRestorer implements Runnable{

	@Override
	public void run() {
		
		boolean noServer=true;
		
		while(noServer){
			try {
				int serverPort=4504;
				Socket s = new Socket("localhost", serverPort); 
				DataOutputStream out =new DataOutputStream( s.getOutputStream());
				out.writeInt(0);
				
				restoreServer(serverPort);
				out.close();
				noServer=false;
			} catch (IOException e) {}

		}
	}
	
	
	public void restoreServer(int serverPort){
		//send round to all other clients
		try{
			MulticastSocket MulticastChannel =new MulticastSocket();
	
	        InetAddress group = InetAddress.getByName("228.5.6.8");  
	        // must use a multicast address

	        
	        // When sending only a multicast message, joining a multicast group is not required
	        //MulticastChannel.joinGroup(group);  
	
	        // multicast is based on UDP ... so same datagram structure
	        String round=new String(100000+" "+serverPort);
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

package Lab4b.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class BackupServer implements Runnable{

	DatagramSocket backupSocket;
	 WordsMonitor words; 
	 gui gui;
	
	public BackupServer(gui gui, DatagramSocket backupSocket2, WordsMonitor words2) {
		this.backupSocket=backupSocket;
		this.words=words;
		this.gui=gui;
	}

	public void run() {
		try{
			System.out.println("\n"+backupSocket.getLocalPort());
			for(;;){
		        byte[] round = new byte[50];
		        DatagramPacket reply = new DatagramPacket(round, round.length);	
		        backupSocket.receive(reply);
		        String message=new String(reply.getData());
		        
		        if(message.contains("close")){
		        	gui.updateDebugArea("Settign client with higher round as new server\n");
		        	backupSocket.close();
		        	break;
		        }
		        
		        System.out.println("Round: " + message +" received");
		        gui.updateDebugArea("Round: " + message +" received");
			}
		}catch(Exception e){}
		
	}

	

}

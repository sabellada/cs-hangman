package Lab4b.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

import Lab4b.server.ConnectionHandler;

public class BackupServer implements Runnable{

	ServerSocket FrontDesk;
	 WordsMonitor words; 
	 gui gui;
	


	public BackupServer(gui gui, ServerSocket FrontDesk,
			WordsMonitor words) {
		this.FrontDesk=FrontDesk;
		this.words=words;
		this.gui=gui;
	}

	public void run() {
		try{
			gui.updateDebugArea("\n Starting backup Server\n");
			for(;;){
				while(true) {

					// a client requests to connect to server
					System.out.println("Waiting for request to connect ");
					Socket ConnectionToOneClient = FrontDesk.accept(); 
					System.out.println("Connect accepted");

					
					//ConnectionToOneClient.close();	//End of the conversation close the connection to the client
				}
			}
		}catch(Exception e){}
		
	}

	

}

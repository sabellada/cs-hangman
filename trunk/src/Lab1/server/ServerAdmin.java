import java.io.*;
import java.net.*;

public class ServerAdmin extends Thread {

	private SocketListener listener;
	
	/*
	 * @param - the SocketListener that the ServerAdmin class will have control of 
	 */
	public ServerAdmin(SocketListener listener){
		this.listener = listener;
	}

	public void run() {
		// keyboard input
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		 
		while (true) {
			try {
				input = reader.readLine();
			} catch(IOException io){
				io.printStackTrace();
				System.exit(1);
			}
			 
			// will shut down if the input contains the letter "s"
			if (input.equalsIgnoreCase("s")) {
				listener.shutdown();
				System.out.println("ServerAdmin: Command acknowledged");

				try {
					DatagramPacket shutdownPacket = new DatagramPacket(SocketListener.SHUTDOWN_BYTE, 
																   SocketListener.SHUTDOWN_BYTE.length,
																   InetAddress.getLocalHost(),
																   69);
					
					DatagramSocket shutdownSocket = new DatagramSocket();
					shutdownSocket.send(shutdownPacket);
					shutdownSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
			else
				System.out.println("ServerAdmin: Invalid Command");
		}		
	}
}

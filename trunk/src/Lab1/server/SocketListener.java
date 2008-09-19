import java.io.IOException;
import java.net.*;

/**
 * SocketListener class is responsible for listening for clients that want to connect
 * to the server on port 69. When a client attempts to connect, SocketListener starts 
 * a new TFTPServer thread to service the client. With this, SocketListener is able
 * constantly monitor port 69.
 */
public class SocketListener {
	public static final byte SHUTDOWN_BYTE[] = "shutdown".getBytes();
	private static int clientCount = 0;
	private DatagramSocket receiveSocket;
	private boolean shutdown = false;
	
	/**
	 * Creates a socket on port 69 and listens for new clients.
	 */
	public SocketListener() {
		try {
	         receiveSocket = new DatagramSocket(69);
	    } catch (SocketException se) {
	         se.printStackTrace();
	         System.exit(1);
	    }
	    
	    System.out.println("SocketListener was started successfully.\n");
	}
	
	/**
	 * Waits for clients to connect, once connected, a thread is made to service
	 * the client.
	 */
	private void connectWithClients() {
		//starts a new server administrator
	    new ServerAdmin(this).start();
	    
	    while(true) {
	    	byte[] data = new byte[100];
	    	DatagramPacket receivePacket = new DatagramPacket(data, data.length);
	    	
	    	System.out.println("SocketListener is waiting for a client..." +
	    					   "\nEnter 's' to quit server...");
	    	try {
	    		receiveSocket.receive(receivePacket);
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    		System.exit(1);
	    	}
	    	
	    	// check for shutdown first
	    	data = receivePacket.getData();
	    	if (shutdown == true)
	    		quitServer();

	    	// decodes the packet to obtain the opcode
	    	byte opcode[] = new byte[2];
	    	opcode[0] = receivePacket.getData()[0];
	    	opcode[1] = receivePacket.getData()[1];
	    		    	
	    	if (validate(receivePacket)) {
	    		System.out.println("SocketListener successfully connected with a client.");
	    		System.out.println("Client count: " + ++clientCount + '\n');
	    		new TFTPServer(receivePacket, opcode).start();
	    	} else 
	    		System.out.println("SocketListener rejected a client: invalid request.");
	    }   
	}
	
	/**
	 * Makes sure that the packet passed has an acceptable message, i.e: either 
	 * a read request (RRQ) or a write request (WRQ).
	 * 
	 * @param packet the packet to be validated 
	 */
	private boolean validate(DatagramPacket packet) {
		byte data[] = packet.getData();
		
		if ((data[0] == TFTPProtocol.RRQ[0] && data[1] == TFTPProtocol.RRQ[1]) ||
			(data[0] == TFTPProtocol.WRQ[0] && data[1] == TFTPProtocol.WRQ[1])) {
				return true;
		}
		
		return false;
	}
	
	public void shutdown() {
		shutdown = true;
	}
	
	public void quitServer() {
	    System.out.print("TFTPServer is shuting down...");
	    System.exit(0);
	}
	
	/**
	 * Starts the SocketListener. 
	 * 
	 * @param args - none
	 */
	public static void main(String[] args) {
		SocketListener sl = new SocketListener();
		sl.connectWithClients();
	}
}

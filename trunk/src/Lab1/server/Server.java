import java.io.IOException;
import java.net.*;

/**
 * TFTPServer services a client and communicates back and forth with it to either
 * answer a read request (RRQ) or a write request (WRQ).
 */
public class TFTPServer extends Thread {
	private DatagramSocket sendReceiveSocket;
	private DatagramPacket sendPacket, receivePacket;
	private byte[] opcode;
	
	/**
	 * Creates a socket for sending and receiving messages with the client.
	 *  
	 * @param receivePacket the packet that TFTPServer is to service
	 * @param opcode		the request that must be satisfied
	 */
	public TFTPServer(DatagramPacket receivePacket, byte[] opcode) {
		try {
			sendReceiveSocket = new DatagramSocket();
		} catch (SocketException se) {
			se.printStackTrace();
			System.exit(1);
		}
		
		this.receivePacket = receivePacket;
		this.opcode = opcode;   
	}
	
	/**
	 * Runs the TFTPServer thread to respond to request.
	 */
	public void run() {		
		if (opcode[0] == TFTPProtocol.WRQ[0] && opcode[1] == TFTPProtocol.WRQ[1])
			writeResponse();
		else if (opcode[0] == TFTPProtocol.RRQ[0] && opcode[1] == TFTPProtocol.RRQ[1])
			readResponse();
		
		sendReceiveSocket.close();
	}
	
	/**
	 * Responds to a read request (RRQ).
	 */
	private void readResponse() {
		sendPacket = new DatagramPacket(TFTPProtocol.DATA, receivePacket.getLength(),
                						receivePacket.getAddress(), receivePacket.getPort());
		
		sendResponse();		
	}
	
	/**
	 * Responds to a write request (WRQ).
	 */
	private void writeResponse() {
		sendPacket = new DatagramPacket(TFTPProtocol.ACK, receivePacket.getLength(),
                						receivePacket.getAddress(), receivePacket.getPort());
		
		sendResponse();		
	}
	
	/**
	 * Sends the response back to the client.
	 * 
	 * @return true if the response was successfully sent
	 */
	private boolean sendResponse() {
		if (sendPacket != null) {
			try {
				sendReceiveSocket.send(sendPacket);
			} catch (IOException e) {
		         e.printStackTrace();
		         System.exit(1);
			}
			
			return true;
		}
		
		return false;
	}
}

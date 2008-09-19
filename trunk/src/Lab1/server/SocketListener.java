import java.io.IOException;
import java.net.*;

public class SocketListener {
	private static int clientCount = 0;
	public static void main (String args[]) {
		try{
			int serverPort = 4504; // the server port
			ServerSocket FrontDesk = new ServerSocket(serverPort);

			// We need only one ServerSocket : FrontDesk is an open generic socket
			// listening for request for connection from client processes.


			while(true) {

				// a client requests to connect to server
				System.out.println("Waiting for request to connect ");
				Socket ConnectionToOneClient = FrontDesk.accept(); 
				System.out.println("Connect accepted");
				// Accept the connect, a virtual channel is now set between the client and the server

				DataInputStream in;		// this stream is to receive a String from client
				DataOutputStream out;	// this stream is to send to a String to client

				// bound the streams to the connect to the client
				in = new DataInputStream( ConnectionToOneClient.getInputStream());
				out =new DataOutputStream( ConnectionToOneClient.getOutputStream());
				System.out.println("Waiting for user");					    
				String messageFromClient = in.readUTF(); 	// read a line of data from the stream
				System.out.println("Username received");											
				// UTF = Universal Text Format

				String messageToClient = "Username received";
				out.writeUTF(messageToClient);
				
	    		System.out.println("SocketListener successfully connected with a client.");
	    		System.out.println("Client count: " + ++clientCount + '\n');
	    		new Server().start(ConnectionToOneClient.);

				ConnectionToOneClient.close();	//End of the conversation close the connection to the client
			}
		} catch(Exception e) {
			System.out.println("Exception caught in Server : "+e.getMessage());
		}
	}
}

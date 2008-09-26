/*
 * gui.java
 *
 * Created on September 3, 2008, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Lab1.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import gui.gameBoard;
/**
 *
 * @author Jerome
 */
public class gui extends gameBoard{
	// this class must extend gameBoard
	private int round = 0;

	/** Creates a new instance of gui */
	public gui(/* declare any argument you need*/) {
		// initialize any variable you need



		// init is a method implemented in gameBoard class and it sets the layout
		// in the interface. You need to call it only once.
		init();
	}

	@Override
	public void resetGameBoard() {
		// clean up the previous game;
		cleanUp();      

		// set the new word
		// BEGINING OF REQUIRED MODIFICATION ******************************
		// contact the server in order to get the word to guess then assign
		// the new word to the variable 'theWord';

		try{
			int serverPort = 4504;
			System.out.println("Request to connect to server");
			Socket s = new Socket("localhost", serverPort);  
			System.out.println("Connection set");
			// If the server process is not on localhost, for instance on 508AA-PC10,
			// while the client is on 508AA-PC-09, then we would have 
			// Socket s = new Socket("508AA-PC10", serverPort);  

			// When creating this Socket, the system tries to set a connection
			// to the server. After the server executes the method "accept", the connection is set.


			// Bound the in/out streams to the socket
			DataInputStream in = new DataInputStream( s.getInputStream());
			DataOutputStream out =new DataOutputStream( s.getOutputStream());


			out.writeInt(round);

			String wordFromServer = in.readUTF(); 
			theWord=wordFromServer;

			round++;
			System.out.println("Received: "+ wordFromServer) ; 
			System.out.println("Current Round Number: "+ round) ;


		}catch (Exception e) {System.out.println("Exception caught in Client " + e.getMessage());}


		// END OF REQUIRED MODIFICATION ***********************************

		setNewWord();   // display the new game
	}
}

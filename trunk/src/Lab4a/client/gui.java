/*
 * gui.java
 *
 * Created on September 3, 2008, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Lab4a.client;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Lab2.Interface.scoreNotification;
import Lab3.client.ScoreReader;
import Lab3.server.ConnectionHandler;


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
		
		try{

			
			
			
			// clean up the previous game;
			cleanUp();      

			//set the URL based on the current round
			String wordURL=new String();
			wordURL="http://www.deskteam.net/sysc4504/gameServer.jsp?games="+round;
			
			/*-------------------------GET WORD FROM URL----------------------------------------*/
			URL url = new URL(wordURL);
			InputStream in = url.openStream();
				byte[] buffer = new byte[1096];
				int receivedBytes;
				String pageContent="";
				
				while( (receivedBytes=in.read(buffer)) != -1){
					pageContent+=new String(buffer);
				}
			in.close();
			/*-------------------------END GET WORD FROM URL----------------------------------------*/
			
			//set theWord to pageContent and trim null characters
			theWord=parseWord(pageContent);
			
			System.out.println("Received: "+ theWord) ; 
			System.out.println("Current Round Number: "+ round) ;

		
			//display new word
			setNewWord();  
			//increment round
			round++;
		
		}catch (Exception e) {System.out.println("Exception caught in Client " + e.getMessage());}
	}
	
	/*
	 * parses the word from the full page content
	 * @param pageContent - the full page content
	 */
	private String parseWord(String pageContent){
		
		pageContent=pageContent.trim();
		int start=pageContent.indexOf("<response>")+10;
		int end=pageContent.indexOf("</response>");
		return pageContent.substring(start, end); 
	}
}

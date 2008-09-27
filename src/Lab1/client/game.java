/*
 * game.java
 *
 * Created on August 19, 2008, 9:38 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Lab1.client;
import java.awt.*;
import javax.swing.*;
import java.net.*;
import java.io.*;


/**
 *
 * @author Jerome
 */
public class game extends JPanel{

	private gui myGameBoard;
	private int roundsPlayed=0;
	/** Creates a new instance of game */
	public game() {   
		myGameBoard = new gui();        
		// no need to modify the two following lines
		// all they do is add the game board to the panel
		setLayout(new BorderLayout());                
		add(myGameBoard, BorderLayout.CENTER);
	}

	/*
	 * Communication with the server
	 */

	/*  LAB 1 : WRITE THE FOLLOWING CODE 
	 *          This client process will contact the server in order to get the word to guess
	 *          This method returns a String that should be send to gui.java
	 *          Refer to the method 'public void resetGameBoard()' in gui.java
	 */
//NOTE: function not used as resetGameBoard accomplishes necessary functionality
//	public  String startNewGame(){
//		String theNewWordToGuess="";      

//		return theNewWordToGuess;
//	}


	// NO NEED TO MODIFY THE REMAINING OF THE CLASS
	// ************************************************************************************
	private static void createAndShowGUI() {        
		JFrame frame = new JFrame("SYSC 4504 Lab");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		game thegame =new game();
		frame.add(thegame, BorderLayout.CENTER);
		frame.setPreferredSize(new Dimension(600,420));
		frame.pack();
		frame.setVisible(true);


	}   
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {                
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});       

	}      
	// ************************************************************************************
}

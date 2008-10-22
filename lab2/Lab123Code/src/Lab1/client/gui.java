/*
 * gui.java
 *
 * Created on September 3, 2008, 4:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package Lab1.client;
import gui.gameBoard;
/**
 *
 * @author Jerome
 */
public class gui extends gameBoard{
    // this class must extend gameBoard
    
    
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
        // the new world to the variable 'theWord';
        
        theWord = "";
        
        // END OF REQUIRED MODIFICATION ***********************************
                
        setNewWord();   // display the new game
    }
}

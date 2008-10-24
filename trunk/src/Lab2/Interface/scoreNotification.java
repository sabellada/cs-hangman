/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab2.Interface;

/**
 *
 * @author Jerome
 */
public interface scoreNotification extends java.rmi.Remote {
	
	/*
	 * RPC to send the score for the round from the client to the server
	 */
    void notify(String player, int score)throws java.rmi.RemoteException; 
    
    /*
     * RPC to multicast the scores, implementation incomplete until lab 3
     */
    String scores()throws java.rmi.RemoteException;
}

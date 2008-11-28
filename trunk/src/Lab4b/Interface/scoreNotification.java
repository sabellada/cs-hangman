/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Lab4b.Interface;

/**
 *
 * @author Joe Oommen and Riley Onabigon
 */
public interface scoreNotification extends java.rmi.Remote {
	
	/*
	 * RPC to send the score for the round from the client to the server
	 */
    void notify(String player, int score)throws java.rmi.RemoteException; 
    

}

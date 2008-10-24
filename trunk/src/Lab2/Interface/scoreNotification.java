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
    void notify(String player, int score)throws java.rmi.RemoteException; 
    
    String scores();
}

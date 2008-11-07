package Lab3.server;

import java.rmi.RemoteException;

import Lab2.Interface.scoreNotification;

public class ScoreUpdater implements scoreNotification{

	ScoreMonitor scoreMonitor;
	
	public ScoreUpdater(ScoreMonitor scoreMonitor){
		this.scoreMonitor=scoreMonitor;
	}
	/*
	 * (non-Javadoc)
	 * @see Lab2.Interface.scoreNotification#notify(java.lang.String, int)
	 */
	@Override
	public void notify(String player, int score) throws RemoteException {
		scoreMonitor.addScore(player, score);
		scoreMonitor.sendScores();
		
	}
	@Override
	public String scores() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}




}

package Lab2.server;

import java.rmi.RemoteException;

import Lab2.Interface.scoreNotification;

public class ScoreUpdater implements scoreNotification{

	ScoreMonitor scoreMonitor;
	
	public ScoreUpdater(ScoreMonitor scoreMonitor){
		this.scoreMonitor=scoreMonitor;
	}
	
	@Override
	public void notify(String player, int score) throws RemoteException {
		scoreMonitor.addScore(player, score);
	}

	//placeholder method to get scores, will be updated in lab 3
	@Override
	public String scores() {
		return scoreMonitor.getScores();
	}

}

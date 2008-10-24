package Lab2.server;

import java.util.ArrayList;

import server.scoreRecords;
import server.words;

public class ScoreMonitor {	

	//keeps track of all scores
	scoreRecords scores=new scoreRecords();


	public synchronized void addScore(String player, int score){
		scores.addRecord(player,score);						
	}

	public synchronized String getScores(){
		return scores.getAllScores();
	}

}

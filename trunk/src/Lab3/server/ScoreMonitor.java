package Lab3.server;

import java.util.ArrayList;

import server.scoreRecords;
import server.words;
/*
 * ScoreMonitor is used to keep the scoreRecords thread safe
 */
public class ScoreMonitor {	

	//keeps track of all scores
	scoreRecords scores=new scoreRecords();

	/*
	 * updates the list of scores
	 * @ player the name of the player sending the score
	 * @score the player's score
	 */
	public synchronized void addScore(String player, int score){
		scores.addRecord(player,score);
	}
	/*
	 * returns the score of the round to the client
	 */
	public synchronized String getScores(){
		return scores.getAllScores();
	}

}

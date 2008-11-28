package Lab4b.client;

import java.util.*;

import server.words;


public class BackupWordsMonitor {
	
	//contains the list of all words generated
	ArrayList<String> wordList = new ArrayList<String>();

	//keeps track of the last round in the list
	int last=0;

	public synchronized String getNext(int round){
		//if first client to get to this round add a new word
		if(round>=last){
			return null;	
		}
		
		//return the word for what round the client is on
		return wordList.get(round);	
	}
	
	public synchronized void addWord(String word){
		wordList.add(word);
		last++;
	}

}

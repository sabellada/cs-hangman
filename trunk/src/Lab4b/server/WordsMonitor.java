package Lab4b.server;

import java.util.*;

import server.words;


public class WordsMonitor {
	
	//contains the list of all words generated
	ArrayList<String> wordList = new ArrayList<String>();
	//generates new words
	words nextWord=new words();
	//keeps track of the last round in the list
	int last=0;

	public synchronized String getNext(int round){
		//if first client to get to this round add a new word
		while(round>=last){
			wordList.add(nextWord.next());
			last++;		
		}
		
		//return the word for what round the client is on
		return wordList.get(round);
		
		
	}



}

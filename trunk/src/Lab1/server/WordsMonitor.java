package Lab1.server;

import java.util.*;

import server.words;


public class WordsMonitor {
	
	ArrayList<String> wordList = new ArrayList<String>();
	words nextWord=new words();
	int last=0;

	public synchronized String getNext(int round){
		if(round>=last){
			wordList.add(nextWord.next());
			last++;		
		}
		
		return wordList.get(round);
		
		
	}

}

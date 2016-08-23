/*
 * File: HangmanLexicon.java
 * -------------------------
 */

import acm.util.*;
import java.io.*;
import java.util.ArrayList;

public class HangmanLexicon {
	
	/** ArrayList in which the words from the lexicon text file are stored */
	private ArrayList<String> wordList = new ArrayList<String>();
	
	/** Constructor reads in the lines from the lexicon text file and stores them in an ArrayList */
	public HangmanLexicon() {
		try {
			BufferedReader rdr = new BufferedReader(new FileReader("ShorterLexicon.txt"));
			while (true) {
				String word = rdr.readLine();
				if (word == null) break;
				wordList.add(word);
			}
			rdr.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return wordList.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return wordList.get(index);
	}
}

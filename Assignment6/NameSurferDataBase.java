import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import acm.util.ErrorException;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {
	private HashMap <String, NameSurferEntry> NSEntryMap = new HashMap<String, NameSurferEntry>(); 
	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the
	 * data in the specified file.  The constructor throws an error
	 * exception if the requested file does not exist or if an error
	 * occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader rdr = new BufferedReader(new FileReader(filename));
			while (true) {
				String dataLine = rdr.readLine();
				if (dataLine == null) break;
				NameSurferEntry entry = new NameSurferEntry(dataLine);
				NSEntryMap.put(entry.getName(), entry);
			}
			rdr.close();
		} catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}
	
	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one
	 * exists.  If the name does not appear in the database, this
	 * method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		return (NSEntryMap.containsKey(capitalized(name))) ? NSEntryMap.get(capitalized(name)) : null;
	}
	
	/** Capitalizes a word 
	  * 
	  * @param word The word to be capitalized, as a String
	  * @return A capitalized version of the input
	  */
	private String capitalized(String word) {
		String capWord = "";
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			capWord += (i == 0) ? Character.toUpperCase(ch) : Character.toLowerCase(ch) ;
		}
		return capWord;
	}
}


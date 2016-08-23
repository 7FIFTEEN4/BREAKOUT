/*
 * File: Hangman.java
 * ------------------
 * This program plays the Hangman game 
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {
	/** Number of guesses user gets */
	private static final int NGUESSES = 8;
	
	/** Running count of how many times the user has guessed */
	private int guessCount = 0;
	
	/** Hangman lexicon object from which random words are chosen */
	private HangmanLexicon lex = new HangmanLexicon();
	
	/** Random number generator */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	/** Random word chosen from Hangman Lexicon */
	private String randomWord = lex.getWord(rgen.nextInt(0, lex.getWordCount() - 1));
	
	/** String that holds the current value of the random word shown to the user, based on correct guesses */
	private String currentState;
	
	/** Hangman Canvas which will be displayed next to the Hangman console program */
	private HangmanCanvas canvas;
	
	/** The init method adds a HangmanCanvas to the game */
	public void init(){
		currentState = startingState();
		canvas = new HangmanCanvas();
		add(canvas);
		canvas.reset();
		canvas.displayWord(currentState);
	}
	
	/** Runs the Hangman game */
	public void run() {
		char userChar;
		println("Welcome to Hangman");
		while (true) {
			displayState();
			displayGuesses();
			userChar = getUserChar();
			if (randomWord.indexOf(userChar) != -1) {
				println("That guess is correct.");
				updateWord(userChar);
				canvas.displayWord(currentState);
			} else {
				println("There are no " + userChar + "'s in the word.");
				guessCount += 1;
				canvas.noteIncorrectGuess(userChar, guessCount);
			}
			if (currentState.equals(randomWord) || guessCount == NGUESSES) {
				break;
			}
		}
		
		if (currentState.equals(randomWord)) {
			println(endMsg("win"));
		} else {
			println(endMsg("lose"));
		}
	} 
	
	/** This method returns a character entered by the user, but the character is only returned when it is a letter */
	private char getUserChar() {
		String input;
		while (true) {
			input = readLine("Your guess: ");
			if (input.length() == 1 && Character.isLetter(input.charAt(0))) {
					break;
				}
			println("That is not a letter. Guess a letter. Please.");
		}
		return Character.toUpperCase(input.charAt(0));
	}
	
	/** Creates a blank version of the random word with dashes replacing 
	  * the letters, it is the starting state of the word displayed to the user 
	  */
	private String startingState() {
		String str = "";
		for (int i = 0; i < randomWord.length(); i++) {
			str += "-";
		}
		return str;
	}
	
	/** If the user's guess is correct, creates an updated version of 
	  * the string that is shown that shown to the user, with the all 
	  * instances of the correct guess included
	  * 
	  * @param currentState The string to be updated
	  * @param uc The character guessed by the user 
	  */
	private String setWordState(String currentState, char uc) {
		String update = "";
		for (int i = 0; i < randomWord.length(); i++) {
			if (currentState.charAt(i) == '-') {				
				update += (randomWord.charAt(i) == uc)? uc : "-";				
			} else {
				update += currentState.charAt(i);
			}
		}
		return update;
	}
	
	/** Prints the current state of the word */
	private void displayState() {
		println("The word now looks like this: " + currentState);
	}
	
	/** Displays the number of guesses the user has remaining */
	private void displayGuesses() {
		println("You have " + (NGUESSES - guessCount) + " more guess" + (NGUESSES - guessCount == 1 ? "." : "es."));
	}
	
	/** Updates the word, then assigns the updated version to the current state, to keep the current state updated
	  * 
	  * @param uc The character that the user entered
	  */
	private void updateWord(char uc) {
		String update = setWordState(currentState, uc);
		currentState = update;
	}
	
	/** Returns an end message based on the result of the game
	  *
	  * @param result The result of the game ("win" or "lose")
	  */
	private String endMsg(String result) {
		return (result.equals("win")? "You guessed the word: " + randomWord + '\n' +  "You " + result + "." : "You are completely hung." + '\n' + "The word was: " + randomWord + '\n' + "You " + result + ".");		
	}
}
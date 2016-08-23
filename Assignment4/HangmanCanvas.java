/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {
	
	/* Constants for the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 300;
	private static final int BEAM_LENGTH = 120;
	private static final int ROPE_LENGTH = 18;
	private static final int SCAFFOLD_OFFSET = 20;
	private static final int HEAD_RADIUS = 25;
	private static final int BODY_LENGTH = 100;
	private static final int ARM_OFFSET_FROM_HEAD = 30;
	private static final int UPPER_ARM_LENGTH = 50;
	private static final int LOWER_ARM_LENGTH = 30;	
	private static final int HIP_RADIUS = 20;
	private static final int LEG_LENGTH = 120;
	private static final int FOOT_LENGTH = 35;
	
	/** The line where all of the incorrect guesses will be displayed */
	private String incorrectGuessLine = "";
	
	/** The GLabel in which the current version of the random word will be stored */
	private GLabel wordDisplay = new GLabel("");

	
	/** Resets the display so that only the scaffold appears */
	public void reset() {
		add(scaffold(SCAFFOLD_OFFSET, SCAFFOLD_OFFSET));
	}

	/** Updates the word on the screen to correspond to the current
	  * state of the game
	  * 
	  * @param word The word to be displayed below the Hangman that is shown to the user
	  */
	public void displayWord(String word) {
		wordDisplay.setFont("Times-35");
		wordDisplay.setLabel("");
		wordDisplay.setLabel(word);
		add(wordDisplay, 50, 380);
	}

	/** Updates the display to correspond to an incorrect guess by the
	  * user, calling this method causes the next body part to appear
	  * on the scaffold and adds the letter to the list of incorrect
	  * guesses that appears at the bottom of the window
	  * 
	  * @param letter The incorrect letter that the user has guessed
	  * @param wrongGuesses The number of times the user has guessed incorrectly
	  */
	public void noteIncorrectGuess(char letter, int wrongGuesses) {
		if (incorrectGuessLine.indexOf(letter) == -1) {
			incorrectGuessLine += letter + " ";
		}
		GLabel incorrectGuessDisplay = new GLabel(incorrectGuessLine);
		incorrectGuessDisplay.setFont("Times-20");
		add(incorrectGuessDisplay, 50, 420);
		
		switch (wrongGuesses) {
			case 1: add(head());
					break;
			case 2: add(body());
					break;
			case 3: add(arm('L'));
					break;
			case 4: add(arm('R'));
					break;
			case 5: add(leg('L'));
					break;
			case 6: add(leg('R'));
					break;
			case 7: add(foot('L'));
					break;
			case 8: add(foot('R'));
					break;
			default: return;
		}
	}
	
	/** The scaffold from which the Hangman is hanged, as a GCompound  */
	private GCompound scaffold(double x, double y) {
		GCompound scfld = new GCompound();
		scfld.setLocation(x, y);
		scfld.add(new GLine(0, 0, BEAM_LENGTH, 0));
		scfld.add(new GLine(0, 0, 0, SCAFFOLD_HEIGHT));
		scfld.add(new GLine(BEAM_LENGTH, 0, BEAM_LENGTH, ROPE_LENGTH));
		return scfld;
	}
	
	/**  The Hangman's head, as a GOval*/
	private GOval head() {
		return new GOval(SCAFFOLD_OFFSET + BEAM_LENGTH - HEAD_RADIUS, SCAFFOLD_OFFSET + ROPE_LENGTH, HEAD_RADIUS * 2, HEAD_RADIUS * 2);	
	}
	
	/** The Hangman's body, as a GLine */
	private GLine body() {
		return new GLine(SCAFFOLD_OFFSET + BEAM_LENGTH, SCAFFOLD_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2, SCAFFOLD_OFFSET + BEAM_LENGTH, SCAFFOLD_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH);
	}
	
	/** The arm method returns an arm
	  * @param side The side of the arm as a character ('L' or 'R')
	  */
	private GCompound arm(char side) {
		GCompound rm = new GCompound();
		rm.add(new GLine((SCAFFOLD_OFFSET + BEAM_LENGTH), (SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD), (SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'?  UPPER_ARM_LENGTH : - UPPER_ARM_LENGTH)), (SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD)));
		rm.add(new GLine((SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'?  UPPER_ARM_LENGTH : - UPPER_ARM_LENGTH)), (SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD), (SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'?  UPPER_ARM_LENGTH : - UPPER_ARM_LENGTH)), SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH));
		return rm;
	}
	
	/** The Hangman's leg, as a GCompound
	  * 
	  * @param side The side of the leg as a character ('L' or 'R')
	  */
	private GCompound leg(char side) {
		GCompound lg = new GCompound();
		lg.add(new GLine((SCAFFOLD_OFFSET + BEAM_LENGTH), (SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH + BODY_LENGTH), (SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'?  HIP_RADIUS : - HIP_RADIUS)), (SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH + BODY_LENGTH)));
		lg.add(new GLine((SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'?  HIP_RADIUS : - HIP_RADIUS)), (SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + ROPE_LENGTH + BODY_LENGTH), (SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'?  HIP_RADIUS : - HIP_RADIUS)), (SCAFFOLD_OFFSET + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH)));
		return lg;
	}
	
	/** The Hangman's leg, as a GCompound */
	private GLine foot(char side) {
		return new GLine((SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'? HIP_RADIUS : - HIP_RADIUS)), (SCAFFOLD_OFFSET + (2 * HEAD_RADIUS) + BODY_LENGTH + LEG_LENGTH), (SCAFFOLD_OFFSET + BEAM_LENGTH + (side == 'R'?  FOOT_LENGTH : - FOOT_LENGTH)),(SCAFFOLD_OFFSET + (2 * HEAD_RADIUS) + BODY_LENGTH + LEG_LENGTH));
	}
}
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width of the application window */
	public static final int APPLICATION_WIDTH = 400;
	
	/** Height of the application window */
	public static final int APPLICATION_HEIGHT = 600;

	/** Width of the game, same as application width, in pixels */	
	private static final int WIDTH = APPLICATION_WIDTH;
	
	/** Height of the game, same as application height, in pixels */
	private static final int HEIGHT = APPLICATION_HEIGHT;
	
	/** Number of rows of bricks */
	private static final int BRICK_ROWS = 10;
	
	/** Number of bricks in a row */
	private static final int BRICKS_PER_ROW = 10;
	
	/** Total number of bricks */
	private static final int NBRICKS = BRICK_ROWS * BRICKS_PER_ROW;
	
	/** Separation between bricks, in pixels */
	private static final int BRICK_SEP = 4;
	
	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (BRICKS_PER_ROW - 1) * BRICK_SEP) / BRICKS_PER_ROW;
	
	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;
	
	/** Distance between the top of the application and the top row of bricks, in pixels */
	private static final int Y_BRICK_OFFSET = 70;
	
	/** Width of the paddle */
	private static final int PADDLE_WIDTH = 85;
	
	/** Height of the paddle */
	private static final int PADDLE_HEIGHT = 5;
	
	/** Distance between the bottom of the application and the paddle, in pixels */
	private static final int Y_PADDLE_OFFSET = 100;
	
	/** Radius of the ball, in pixels */
	private static final int BALL_RADIUS = 5;
	
	/** Number of turns the user gets to play before the game is over */
	private static final int NTURNS = 3;
	
	/** Instance variable that keeps track of how many lives the user has left */
	private int turnsLeft = NTURNS;
	
	/** Instance variable that keeps track of how many bricks have been hit */
	private int brickCount = 0;
	
	/** Instance variable that keeps track of how many times the paddle has been hit */
	private int paddleHitCount = 0;
	
	/** Game's introduction message, as a GLabel */
	private GLabel intro = new GLabel("Click to Play Breakout");
	
	/** Game's paddle , as a GRect */
	private GRect paddle = new GRect((WIDTH - PADDLE_WIDTH) / 2, HEIGHT - Y_PADDLE_OFFSET, PADDLE_WIDTH, PADDLE_HEIGHT);
	
	/** Game's ball, as a GOval */
	private GOval ball = new GOval ((WIDTH - 2 * BALL_RADIUS) / 2, (HEIGHT - 2 * BALL_RADIUS) / 2, BALL_RADIUS * 2, BALL_RADIUS * 2);
	
	/** Ball's x velocity */
	private double xVel = -3;
	
	/** Ball's y velocity */
	private double yVel = Math.sqrt(25 - Math.pow(xVel, 2));
	
	/** The init method sets up the intro, the bricks, the paddle, and the ball */
	public void init() {
		setUpIntro();
		setUpBricks();
		setUpPaddle();
		setUpBall();
	}
	
	/** The run method removes the intro message, runs 
	 * the breakout game until all the bricks have been hit, or 
	 * until there are no turns left, and then displays an end
	 * message based on the result of the game
	 */
	public void run() {
		waitForClick();
		remove(intro);
		while(true) {
			double delay = 15 - paddleHitCount / 7;
			moveBall();
			checkCollision();
			if (brickCount == NBRICKS || turnsLeft == 0) {
				break;
			}
			pause(delay);
		}
		if (brickCount == NBRICKS) {
			runEndSequence("WIN");
		} else {
			runEndSequence("LOSE");
		}
	}
	
	/** The mouseMoved method moves the paddle based 
	 * on the x coordinate of the mouse
	 */
	public void mouseMoved(MouseEvent e) {
		double paddlePosition = e.getX();
		if (paddlePosition < 370 && paddlePosition > 30) {
			paddle.setLocation(e.getX() - (PADDLE_WIDTH / 2), HEIGHT - Y_PADDLE_OFFSET);
		}
	}
	/** The setUpIntro method adds the intro message to the game */
	private void setUpIntro() {
		add(intro);
		intro.setFont("Impact-30");
		intro.setColor(new Color(255, 0, 255));
		intro.setLocation((WIDTH - intro.getWidth()) / 2, (HEIGHT + 4 * intro.getHeight()) / 2);
	}
	
	/** This method sets up the bricks for the game */
	private void setUpBricks() {
		double sX;
		double sY;
		
		for (int col = 0; col < BRICKS_PER_ROW; col ++) {
			sX = (BRICK_WIDTH + BRICK_SEP) * col + BRICK_SEP / 2;
			for (int row = 0; row < BRICK_ROWS; row++) {
				sY = (BRICK_HEIGHT + BRICK_SEP) * row + Y_BRICK_OFFSET;
				/*	If the row number is even/zero, the number assigned 
				 * 	to the row is the row number plus one, and if the row 
				 * 	number is odd, the assigned number is just the actual 
				 * 	row number. I did this so that each set of two rows 
				 * 	with the same color could share the number that determines
				 * 	their color.
				*/
				if (row % 2 == 0) {
				add(colorBrick(sX, sY, row + 1));
				} else {
				add(colorBrick(sX, sY, row));
				}
			}
		}
	}
	
	/** 
	 * This method returns the bricks which will be 
	 * added to the game, in the setUpBricks method
	 * 
	 * @param x The x coordinate of the brick
	 * 
	 * @param y The y coordinate of the brick
	 * 
	 * @param colorInt The number which determines the color 
	 * 		  of the brick, in coordination with the color method
	 */
	private GRect colorBrick(double x, double y, int colorInt) {
		GRect brick = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
		brick.setColor(color(colorInt));
		brick.setFilled(true);
		return brick;
	}
	
	/** 
	 * This method assigns numbers to colors, 
	 * so that the color of a given row of bricks                                
	 * can be determined using the index variable 
	 * for counting the rows, in the setUpBricks 
	 * method
	 * 
	 * @param i The number which is used to determine the color
	 */
	private Color color(int i) {
		switch (i) {
		case 1: return Color.RED;
		case 3: return Color.ORANGE;
		case 5: return Color.YELLOW;
		case 7: return Color.GREEN;
		case 9: return Color.CYAN;
		default: return null;
		}
	}
	
	/** The setUpPaddle method adds the paddle to the game */
	private void setUpPaddle() {
		addMouseListeners();
		paddle.setFilled(true);
		add(paddle);
	}
	
	/** The setUpBall method adds the ball to the game */
	private void setUpBall() {
		ball.setFilled(true);
		add(ball);
	}
	
	/** The moveBall method moves the ball according to the values of 
	 * the x and y velocities, bounces the ball off the left, right,
	 * and top walls, and resets the ball after the ball hits the bottom
	 * of the screen (meaning that the ball has passed the paddle without
	 * hitting it)
	 */
	private void moveBall() {
		ball.move(xVel, yVel);
		if (ball.getX() + 2 * BALL_RADIUS > WIDTH || ball.getX() < 0) {
			xVel *= -1;
			/* This ball.move is present so that if the ball ever 
			 * gets past the boundaries of the left and right walls,
			 * the ball does not stay there and repeatedly change its
			 * xVel value back and forth from itself and -1 multiplied 
			 * by itself
			 */
			ball.move(xVel, yVel);
		}
		if (ball.getY() <= 0) {
			yVel *= -1;
		}
		if (ball.getY() >= HEIGHT ) {
			turnsLeft -=1;
			remove(ball);
			displayTurnsLeft();
			add(ball, (WIDTH - 2 * BALL_RADIUS) / 2, (HEIGHT - 2 * BALL_RADIUS) / 2);
		}
	}
	
	/** The displayTurnsLeft method adds a message that tells 
	 * the user how many turns they have left to the game
	 */
	private void displayTurnsLeft() {
		GLabel turnsBoard = new GLabel("TURNS REMAINING: " + turnsLeft);
		turnsBoard.setFont("Impact-35");
		add(turnsBoard, (WIDTH - turnsBoard.getWidth()) / 2, HEIGHT / 2 );
		waitForClick();
		remove(turnsBoard);
	}
	
	/** The checkCollision method checks what the collider object hit: 
	 * If it hit the paddle, the method bounces the ball upward at an angle 
	 * determined by where the ball made contact on the paddle
	 * If it hit a brick, it removes the brick and bounces the ball downward,
	 * and takes away a brick from the running count of bricks
	 */
	private void checkCollision() {
		GObject collider = getCollider();
		double ballC = ball.getX() + BALL_RADIUS;
		
		if (collider == paddle) {
			paddleHitCount += 1;
				if ((ballC - paddle.getX()) / PADDLE_WIDTH > 0.5) {
					xVel = 4.5 * (((ballC - paddle.getX()) - (PADDLE_WIDTH / 2)) / ((PADDLE_WIDTH + 2 * BALL_RADIUS) / 2));
					yVel = -(Math.sqrt(25 - Math.pow(xVel, 2.0)));
				} else if ((ballC - paddle.getX()) / PADDLE_WIDTH < 0.5) {
					xVel = -4.5 * ((PADDLE_WIDTH - ballC + paddle.getX() - (PADDLE_WIDTH / 2)) / ((PADDLE_WIDTH + 2 * BALL_RADIUS) / 2));
					yVel = -(Math.sqrt(25 - Math.pow(xVel, 2.0)));
				}
			
		} else if (collider != null){
			remove(collider);
			yVel *= -1;
			brickCount += 1;
		} 
	}
	
	/** The getCollider checks if any of the 4 corner points of the square that the ball 
	 * is inscribed in has made contact with another object, and if contact has 
	 * been made, that object is returned
	 */
	private GObject getCollider() {
		if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(ball.getX(), ball.getY()) != null) {
			return getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			return getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		} else {
			return null;
		}
	} 
	
	/** The runEndSequence method displays a message that 
	 * displays whether the user won or lost the game 
	 * 
	 * @param result Result of the game (Win or Lose)
	 */
	private void runEndSequence(String result) {
		remove(ball);
		GLabel endMsg = new GLabel("YOU " + result);
		endMsg.setFont("Impact-50");
		add(endMsg, (WIDTH - endMsg.getWidth()) / 2, HEIGHT / 2);
	}
}
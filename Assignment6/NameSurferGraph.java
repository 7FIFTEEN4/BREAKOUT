/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	public static final int GRAPH_HEIGHT = APPLICATION_HEIGHT - 175;
	
	private ArrayList<NameSurferEntry> graphEntries = new ArrayList<NameSurferEntry>();
	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
		initDecadeLines();
	}
	
	
	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		removeAll();
		initDecadeLines();
		graphEntries.clear();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display.
	 * Note that this method does not actually draw the graph, but
	 * simply stores the entry; the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		graphEntries.add(entry);
	}
	
	
	/**
	 * Updates the display image by deleting all the graphical objects
	 * from the canvas and then reassembling the display according to
	 * the list of entries. Your application must call update after
	 * calling either clear or addEntry; update is also called whenever
	 * the size of the canvas changes.
	 */
	public void update() {
		for (int i = 0; i < graphEntries.size(); i++) {
			int lineColor = 15000 * (i + 1);
			graphEntry(graphEntries.get(i), lineColor);
		}
	}
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	private void initDecadeLines() {
		for (int i = 0; i < NDECADES; i++) {
			double xPos = decadeUnit * i;
			add(new GLine(xPos, 0, xPos, GRAPH_HEIGHT));
			add(new GLabel("" + (1900 + 10 * i), xPos, GRAPH_HEIGHT - 12.5));
		}
		add(new GLine(0, GRAPH_HEIGHT - GRAPH_MARGIN_SIZE, APPLICATION_WIDTH, GRAPH_HEIGHT - GRAPH_MARGIN_SIZE));
		add(new GLine(0, GRAPH_MARGIN_SIZE, APPLICATION_WIDTH, GRAPH_MARGIN_SIZE));
	}
	
	private void graphEntry(NameSurferEntry entry, int lineColor) {
		GLine[] graphLines = new GLine[NDECADES];
		GLabel[] graphLabels = new GLabel[NDECADES];
		double rankUnit = (double) (GRAPH_HEIGHT - 2 * GRAPH_MARGIN_SIZE) / MAX_RANK;
		for (int i = 0; i < NDECADES - 1; i++) {
			String name = entry.getName();
			int firstRank = entry.getRank(i);
			int secondRank = entry.getRank(i + 1);
			double startX = decadeUnit * (i);
			double endX = decadeUnit * (i + 1);
			double startY = (firstRank != 0) ? firstRank * rankUnit + GRAPH_MARGIN_SIZE : GRAPH_HEIGHT - GRAPH_MARGIN_SIZE;
			double endY = (secondRank != 0) ? secondRank * rankUnit + GRAPH_MARGIN_SIZE : GRAPH_HEIGHT - GRAPH_MARGIN_SIZE ;
			graphLines[i] = new GLine(startX, startY, endX, endY);
			graphLines[i].setColor(new Color(lineColor));
			add(graphLines[i]);
			graphLabels[i] = new GLabel(name + " " + ((firstRank != 0) ? firstRank : "**"), startX, startY);
			graphLabels[i].setColor(new Color(lineColor));
			add(graphLabels[i]);
			if (i == NDECADES - 2) {
				graphLabels[i + 1] = new GLabel(name + " " + ((secondRank != 0) ? secondRank : "**"), endX, endY);
				graphLabels[i + 1].setColor(new Color(lineColor));
				add(graphLabels[i + 1]);
			}
		}
	}
	
	private double decadeUnit = (double) APPLICATION_WIDTH / NDECADES;
	
}

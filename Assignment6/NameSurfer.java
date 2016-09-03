/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private NameSurferDataBase db = new NameSurferDataBase(NAMES_DATA_FILE);
	private NameSurferGraph graph = new NameSurferGraph();
	private JTextField nameField;
	private JButton graphButton;
	private JButton clearButton;
	/**
	 * This method has the responsibility for reading in the data base
	 * and initializing the interactors at the top of the window.
	 */
	public void init() {
		nameField =  new JTextField(NAME_FIELD_WIDTH);
		graphButton = new JButton("Graph");
		clearButton = new JButton("Clear");
		add(new JLabel("Name: "), NORTH);
		add(nameField, NORTH);
		add(graphButton, NORTH);
		add(clearButton, NORTH);
		add(graph);
		addActionListeners();	
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are
	 * clicked, so you will have to define a method to respond to
	 * button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmnd = e.getActionCommand();
		if (cmnd.equals("Graph")) {
			println("Graph: " + db.findEntry(nameField.getText()));
			if (db.findEntry(nameField.getText()) != null) {
				graph.addEntry(db.findEntry(nameField.getText()));
				graph.update();
			}
		}
		if (cmnd.equals("Clear")) {
			graph.clear();
		}
	}
	
}

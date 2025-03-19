package edu.exampleuni.ums.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EventController {
	@FXML
	private Label eventLabel;

	@FXML
	public void initialize() {
		eventLabel.setText("Event Management");
	}
}
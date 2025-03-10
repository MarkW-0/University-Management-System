package edu.exampleuni.ums;

import javafx.scene.layout.*;

public class Spacer extends Region {
	public Spacer() {
		super();
		HBox.setHgrow(this, Priority.ALWAYS);
	}
}
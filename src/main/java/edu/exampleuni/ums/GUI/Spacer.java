package edu.exampleuni.ums.GUI;

import javafx.scene.layout.*;

public class Spacer extends Region {
	public Spacer() {
		super();
		HBox.setHgrow(this, Priority.ALWAYS);
	}
}
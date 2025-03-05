package edu.exampleuni.ums;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuItem extends VBox {
	@FXML private Label name;

	public MenuItem() {
		FXMLLoader fxmlLoader = new FXMLLoader(MenuItem.class.getResource("MenuItem.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String getName() {
		return this.nameProperty().get();
	}

	public void setName(String value) {
		this.nameProperty().set(value);
	}

	public StringProperty nameProperty() {
		return this.name.textProperty();
	}

}
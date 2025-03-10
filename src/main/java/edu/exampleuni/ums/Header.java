package edu.exampleuni.ums;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class Header extends HBox {
	@FXML private Label userLabel;
	@FXML public Button logoutButton;

	public Header() {
		FXMLLoader fxmlLoader = new FXMLLoader(Header.class.getResource("Header.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public String getUserLabel() {
		return this.userLabelProperty().get();
	}

	public void setUserLabel(String value) {
		this.userLabelProperty().set(value);
	}

	public StringProperty userLabelProperty() {
		return this.userLabel.textProperty();
	}

}
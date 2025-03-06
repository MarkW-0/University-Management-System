package edu.exampleuni.ums;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class LoginScreen extends StackPane {
	@FXML public TextField usernameField;
	@FXML public PasswordField passwordField;
	@FXML public Button loginButton;
	@FXML public Label errorMessage;

	public LoginScreen() {
		FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("LoginScreen.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class LoginScreen extends StackPane {
	@FXML public TextField usernameField;
	@FXML public PasswordField passwordField;
	@FXML public Button loginButton;
	@FXML public Label errorMessage;

	public LoginScreen(MainApp mainApp) {
		FXMLLoader fxmlLoader = new FXMLLoader(LoginScreen.class.getResource("LoginScreen.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		// Login button action
		this.loginButton.setOnAction(e -> {
			mainApp.user = mainApp.authService.authenticate(this.usernameField.getText(), this.passwordField.getText());
			if (mainApp.user == null) {
				this.errorMessage.setVisible(true);
				return;
			}
			mainApp._setScene(new MainLayout(mainApp), 1024, 768);
			mainApp.stage.setMaximized(true);
		});
	}
}
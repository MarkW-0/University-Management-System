package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.UserAuth;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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
			String username = this.usernameField.getText();
			byte[] password = this.passwordField.getText().getBytes(StandardCharsets.UTF_8);
			this.passwordField.setText("");
			for(UserAuth userAuth : mainApp.authService.getAllUserAuths()) {
				if(userAuth.getUsername().equals(username)) {
					if (userAuth.login(password)) {
						mainApp.userAuth = userAuth;
						mainApp._setScene(new MainLayout(mainApp), 1024, 768);
						mainApp.stage.setMaximized(true);
						return;
					}
				}
			}
			this.errorMessage.setVisible(true);
		});
	}
}
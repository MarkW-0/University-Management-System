package edu.exampleuni.ums.views;

import edu.exampleuni.ums.services.AuthService;
import edu.exampleuni.ums.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginScreen {
	private final Stage stage;
	private final AuthService authService;

	public LoginScreen(Stage stage) {
		this.stage = stage;
		this.authService = new AuthService();
	}

	public Scene getScene() {
		VBox loginForm = new VBox(15);
		loginForm.setAlignment(Pos.CENTER);
		loginForm.setPadding(new Insets(30));
		loginForm.setMaxWidth(400);
		loginForm.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");

		// UI components
		TextField usernameField = new TextField();
		PasswordField passwordField = new PasswordField();
		Button loginButton = new Button("Login");
		Label errorMessage = new Label();

		// Add components to form
		loginForm.getChildren().addAll(
				new Label("University Management System"),
				new Label("Username"), usernameField,
				new Label("Password"), passwordField,
				loginButton, errorMessage
		);

		// Login button action
		loginButton.setOnAction(e -> {
			String username = usernameField.getText();
			String password = passwordField.getText();

			User user = authService.authenticate(username, password);
			if (user != null) {
				// Navigate to main application
				MainApplicationLayout mainApp = new MainApplicationLayout(stage, user);
				stage.setScene(mainApp.getScene());
				stage.setMaximized(true);
			} else {
				errorMessage.setText("Invalid username or password!");
				errorMessage.setVisible(true);
			}
		});

		// Wrap in a StackPane
		StackPane rootPane = new StackPane();
		rootPane.getChildren().add(loginForm);
		return new Scene(rootPane, 800, 600);
	}
}
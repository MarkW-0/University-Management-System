package edu.exampleuni.ums;

import edu.exampleuni.ums.views.LoginScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("University Management System");
		primaryStage.setMinWidth(1024);
		primaryStage.setMinHeight(768);

		// Show the login screen
		LoginScreen loginScreen = new LoginScreen(primaryStage);
		primaryStage.setScene(loginScreen.getScene());
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
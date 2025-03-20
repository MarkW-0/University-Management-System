package edu.exampleuni.ums;

import edu.exampleuni.ums.GUI.*;
import edu.exampleuni.ums.models.*;
import edu.exampleuni.ums.services.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;


public class MainApp extends Application {
	public User user;
	public Stage stage;
	public final AuthService authService = new AuthService();
	public final SubjectService subjectService = new SubjectService();
	public final CourseService courseService = new CourseService();
	public final EventService eventService = new EventService();

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("University Management System");
		this._setScene(new LoginScreen(this), 800, 600);
		stage.show();
	}

	public void _setScene(Parent content, double width, double height) {
		Scene scene = new Scene(content, width, height);
		var stylesheet = MainApp.class.getResource("style.css");
		if (stylesheet != null) scene.getStylesheets().add(stylesheet.toExternalForm());
		else System.err.println("missing stylesheet");
		this.stage.setScene(scene);
	}


	// Main method
	public static void main(String[] args) {
		launch(args);
	}
}
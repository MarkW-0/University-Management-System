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
	public final ExcelService excelService;
	public final SubjectService subjectService;
	public final CourseService courseService;
	public final EventService eventService;
	public final UserService userService;

	public MainApp() {
		this.excelService = new ExcelService();
		this.subjectService = new SubjectService(this.excelService);
		this.courseService = new CourseService(this.excelService);
		this.eventService = new EventService(this.excelService);
		this.userService = new UserService();
	}

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
}
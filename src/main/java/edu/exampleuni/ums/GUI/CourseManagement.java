package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;

public class CourseManagement extends Management<Course> {
	public CourseManagement(MainApp mainApp) {
		super(mainApp, mainApp.courseService, CourseEditActions::new, CourseEditActions::createEditDialog, "CourseManagement.fxml");
	}
}
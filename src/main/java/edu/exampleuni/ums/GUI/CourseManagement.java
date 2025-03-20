package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.models.Course;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class CourseManagement extends VBox {
	@FXML public TableView<Course> courseTable;
	@FXML public Button addButton;

	public CourseManagement() {
		FXMLLoader fxmlLoader = new FXMLLoader(CourseManagement.class.getResource("CourseManagement.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
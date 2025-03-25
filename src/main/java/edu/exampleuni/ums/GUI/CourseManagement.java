package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.Course;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class CourseManagement extends VBox {
	@FXML public TableView<Course> courseTable;
	@FXML public Button addButton;

	public CourseManagement(MainApp mainApp) {
		FXMLLoader fxmlLoader = new FXMLLoader(CourseManagement.class.getResource("CourseManagement.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!mainApp.userAuth.getRole().equals("ADMIN")) {
			this.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (mainApp.userAuth.getRole().equals("ADMIN")) {
			TableColumn<Course, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new CourseManagementAdminActionCell(mainApp));
			this.courseTable.getColumns().add(actionCol);
		}
		// Add Course button functionality
		this.addButton.setOnAction(e -> {
			Dialog<Course> dialog = CourseManagementAdminActionCell.createEditDialog(null);
			dialog.showAndWait().ifPresent(newCourse -> {
				mainApp.courseService.addCourse(newCourse);
				this.courseTable.getItems().add(newCourse);
			});
		});

		// Add data
		this.courseTable.getItems().addAll(mainApp.courseService.getAllCourses());
	}
}
package edu.exampleuni.ums;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class CourseManagementAdminActionCell extends ActionCell<Course> {
	{
		this.editBtn.setOnAction(event -> {
			Course course = this.getTableView().getItems().get(getIndex());
			Dialog<Course> dialog = Window.createCourseEditDialog(course);
			dialog.showAndWait().ifPresent(editedCourse -> {
				Window.courseService.updateCourse(editedCourse);
				this.getTableView().refresh();
			});
		});

		this.deleteBtn.setOnAction(event -> {
			Course course = this.getTableView().getItems().get(getIndex());
			// Show confirmation dialog
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Delete Course");
			alert.setHeaderText("Delete Course: " + course.getCourseName());
			alert.setContentText("Are you sure you want to delete this course?");

			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					Window.courseService.deleteCourse(course);
					this.getTableView().getItems().remove(getIndex());
				}
			});
		});
	}
}
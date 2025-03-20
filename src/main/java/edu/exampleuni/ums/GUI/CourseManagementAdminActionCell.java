package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.models.Course;
import edu.exampleuni.ums.MainApp;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CourseManagementAdminActionCell extends ActionCell<Course> {
	CourseManagementAdminActionCell(MainApp mainApp) {
		super();
		this.editBtn.setOnAction(event -> {
			Course course = this.getTableView().getItems().get(getIndex());
			Dialog<Course> dialog = CourseManagementAdminActionCell.createCourseEditDialog(course);
			dialog.showAndWait().ifPresent(editedCourse -> {
				mainApp.courseService.updateCourse(editedCourse);
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
					mainApp.courseService.deleteCourse(course);
					this.getTableView().getItems().remove(getIndex());
				}
			});
		});
	}
	public static Dialog<Course> createCourseEditDialog(Course existingCourse) { // todo move to own class?
		Dialog<Course> dialog = new Dialog<>();
		dialog.setTitle(existingCourse == null ? "Add Course" : "Edit Course");

		ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField codeField = new TextField();
		codeField.setPromptText("Course Code");

		TextField nameField = new TextField();
		nameField.setPromptText("Course Name");

		TextField subjectField = new TextField();
		subjectField.setPromptText("Subject");

		TextField sectionField = new TextField();
		sectionField.setPromptText("Section");

		TextField teacherField = new TextField();
		teacherField.setPromptText("Teacher");

		TextField capacityField = new TextField();
		capacityField.setPromptText("Capacity");

		TextField locationField = new TextField();
		locationField.setPromptText("Location");

		// Pre-fill fields if editing an existing course
		if (existingCourse != null) {
			codeField.setText(existingCourse.getCode());
			nameField.setText(existingCourse.getCourseName());
			subjectField.setText(existingCourse.getSubject());
			sectionField.setText(existingCourse.getSection());
			teacherField.setText(existingCourse.getTeacher());
			capacityField.setText(existingCourse.getCapacity());
			locationField.setText(existingCourse.getLocation());
		} else {
			// Default values for new course
			sectionField.setText("Section 1");
			capacityField.setText("30");
			locationField.setText("Room 101");
		}

		grid.add(new Label("Course Code:"), 0, 0);
		grid.add(codeField, 1, 0);
		grid.add(new Label("Course Name:"), 0, 1);
		grid.add(nameField, 1, 1);
		grid.add(new Label("Subject:"), 0, 2);
		grid.add(subjectField, 1, 2);
		grid.add(new Label("Section:"), 0, 3);
		grid.add(sectionField, 1, 3);
		grid.add(new Label("Teacher:"), 0, 4);
		grid.add(teacherField, 1, 4);
		grid.add(new Label("Capacity:"), 0, 5);
		grid.add(capacityField, 1, 5);
		grid.add(new Label("Location:"), 0, 6);
		grid.add(locationField, 1, 6);

		dialog.getDialogPane().setContent(grid);

		// Convert the result to a Course when the save button is clicked
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == saveButtonType) {
				String code = codeField.getText().trim();
				String name = nameField.getText().trim();
				String subject = subjectField.getText().trim();
				String section = sectionField.getText().trim();
				String teacher = teacherField.getText().trim();
				String capacity = capacityField.getText().trim();
				String location = locationField.getText().trim();

				// Validate input
				if (code.isEmpty() || name.isEmpty() || subject.isEmpty() || teacher.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid Input");
					alert.setHeaderText(null);
					alert.setContentText("Course Code, Name, Subject, and Teacher cannot be empty!");
					alert.showAndWait();
					return null;
				}

				// If editing existing course, update values; otherwise create new Course
				if (existingCourse != null) {
					existingCourse.setCode(code);
					existingCourse.setCourseName(name);
					existingCourse.setSubject(subject);
					existingCourse.setSection(section);
					existingCourse.setTeacher(teacher);
					existingCourse.setCapacity(capacity);
					existingCourse.setLocation(location);
					return existingCourse;
				} else {
					return new Course(code, name, subject, section, teacher, capacity, location);
				}
			}
			return null;
		});

		return dialog;
	}

}
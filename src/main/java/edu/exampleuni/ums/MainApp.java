package edu.exampleuni.ums;

import edu.exampleuni.ums.GUI.*;
import edu.exampleuni.ums.models.*;
import edu.exampleuni.ums.services.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;


public class MainApp extends Application {
	public User user;
	public Stage stage;
	public final AuthService authService = new AuthService();
	public final SubjectService subjectService = new SubjectService();
	public final CourseService courseService = new CourseService();

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("University Management System");
		this._setScene(new LoginScreen(this), 800, 600);
		stage.show();
	}

	public static Dialog<Subject> createSubjectEditDialog(Subject existingSubject) {
		Dialog<Subject> dialog = new Dialog<>();
		// Create a dialog for the subject form
		dialog.setTitle(existingSubject == null ? "Add Subject" : "Edit Subject");

		// Set the button types
		ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

		// Create the form
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		TextField codeField = new TextField();

		codeField.setPromptText("Subject Code");
		TextField nameField = new TextField();
		nameField.setPromptText("Subject Name");
		// Pre-fill fields if editing a subject that is already there
		if (existingSubject != null) {
			codeField.setText(existingSubject.getCode());
			nameField.setText(existingSubject.getSubjectName());
		}
		grid.add(new Label("Subject Code:"), 0, 0);
		grid.add(codeField, 1, 0);
		grid.add(new Label("Subject Name:"), 0, 1);
		grid.add(nameField, 1, 1);
		dialog.getDialogPane().setContent(grid);
		// Convert the result to a Subject when the save button is clicked
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == saveButtonType) {
				String code = codeField.getText().trim();
				String name = nameField.getText().trim();
				// Validate input
				if (code.isEmpty() || name.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid Input");
					alert.setHeaderText(null);
					alert.setContentText("Subject Code and Name cannot be empty!");
					alert.showAndWait();
					return null;
				}
				// If editing existing subject, update values; otherwise create new Subject
				if (existingSubject != null) {
					existingSubject.setCode(code);
					existingSubject.setSubjectName(name);
					return existingSubject;
				} else {
					return new Subject(code, name);
				}
			}
			return null;
		});
		return dialog;
	}

	public static Dialog<Course> createCourseEditDialog(Course existingCourse) {
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
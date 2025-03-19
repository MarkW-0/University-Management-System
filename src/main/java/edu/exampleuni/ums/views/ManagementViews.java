package edu.exampleuni.ums.views;

import edu.exampleuni.ums.models.Course;
import edu.exampleuni.ums.models.Subject;
import edu.exampleuni.ums.services.CourseService;
import edu.exampleuni.ums.services.SubjectService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ManagementViews {
	private static final String PRIMARY_COLOR = "#3498db";
	private static final String SECONDARY_COLOR = "#2c3e50";
	private static final String ACCENT_COLOR = "#e74c3c";
	private static final SubjectService subjectService = new SubjectService();
	private static final CourseService courseService = new CourseService();

	public static VBox createSubjectManagement(String userRole) {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));

		Label titleLabel = new Label("Subject Management");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		// Search and filter controls
		HBox controls = new HBox(10);
		controls.setAlignment(Pos.CENTER_LEFT);

		TextField searchField = new TextField();
		searchField.setPromptText("Search subjects...");
		searchField.setPrefWidth(300);

		Button addButton = new Button("Add Subject");
		addButton.setStyle("-fx-background-color: " + PRIMARY_COLOR + "; -fx-text-fill: white;");

		// Add button functionality
		addButton.setOnAction(e -> {
			Dialog<Subject> dialog = createSubjectEditDialog(null);
			dialog.showAndWait().ifPresent(newSubject -> {
				subjectService.addSubject(newSubject);
				TableView<Subject> tableView = (TableView<Subject>) content.getChildren().get(2);
				tableView.getItems().add(newSubject);
			});
		});

		if (!"ADMIN".equals(userRole)) {
			addButton.setDisable(true);
		}

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		controls.getChildren().addAll(searchField, spacer, addButton);

		// Subjects table
		TableView<Subject> subjectsTable = new TableView<>();

		TableColumn<Subject, String> codeCol = new TableColumn<>("Subject Code");
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
		codeCol.setPrefWidth(150);

		TableColumn<Subject, String> nameCol = new TableColumn<>("Subject Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setPrefWidth(400);

		subjectsTable.getColumns().addAll(codeCol, nameCol);
		subjectsTable.getItems().addAll(subjectService.getAllSubjects());

		// Add action column if ADMIN
		if ("ADMIN".equals(userRole)) {
			subjectsTable.getColumns().add(createSubjectActionColumn(subjectsTable));
		}

		VBox.setVgrow(subjectsTable, Priority.ALWAYS);

		// Add all elements to content
		content.getChildren().addAll(titleLabel, controls, subjectsTable);
		return content;
	}

	private static TableColumn<Subject, Void> createSubjectActionColumn(TableView<Subject> table) {
		TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
		actionCol.setPrefWidth(150);
		actionCol.setCellFactory(param -> new TableCell<Subject, Void>() {
			private final Button editBtn = new Button("Edit");
			private final Button deleteBtn = new Button("Delete");
			private final HBox actionButtons = new HBox(5, editBtn, deleteBtn);

			{
				editBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
				deleteBtn.setStyle("-fx-background-color: " + ACCENT_COLOR + "; -fx-text-fill: white;");

				editBtn.setOnAction(event -> {
					Subject subject = getTableView().getItems().get(getIndex());
					Dialog<Subject> dialog = createSubjectEditDialog(subject);
					dialog.showAndWait().ifPresent(editedSubject -> {
						subjectService.updateSubject(editedSubject);
						table.refresh();
					});
				});

				deleteBtn.setOnAction(event -> {
					Subject subject = getTableView().getItems().get(getIndex());
					// Show confirmation dialog
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Delete Subject");
					alert.setHeaderText("Delete Subject: " + subject.getName());
					alert.setContentText("Are you sure you want to delete this subject?");

					alert.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							subjectService.deleteSubject(subject);
							getTableView().getItems().remove(getIndex());
						}
					});
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(actionButtons);
				}
			}
		});
		return actionCol;
	}

	private static Dialog<Subject> createSubjectEditDialog(Subject existingSubject) {
		Dialog<Subject> dialog = new Dialog<>();
		dialog.setTitle(existingSubject == null ? "Add Subject" : "Edit Subject");

		ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField codeField = new TextField();
		codeField.setPromptText("Subject Code");

		TextField nameField = new TextField();
		nameField.setPromptText("Subject Name");

		// Pre-fill fields if editing an existing subject
		if (existingSubject != null) {
			codeField.setText(existingSubject.getCode());
			nameField.setText(existingSubject.getName());
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
					existingSubject.setName(name);
					return existingSubject;
				} else {
					return new Subject(code, name);
				}
			}
			return null;
		});

		return dialog;
	}

	public static VBox createCourseManagement(String userRole) {
		VBox content = new VBox(20);
		content.setPadding(new Insets(20));

		Label titleLabel = new Label("Course Management");
		titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

		// Tab pane for different course views
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

		// All Courses Tab
		Tab allCoursesTab = new Tab("All Courses");
		VBox allCoursesContent = new VBox(10);
		allCoursesContent.setPadding(new Insets(10));

		// Search and controls
		HBox controls = new HBox(10);
		controls.setAlignment(Pos.CENTER_LEFT);

		TextField searchField = new TextField();
		searchField.setPromptText("Search courses...");
		searchField.setPrefWidth(300);

		ComboBox<String> subjectFilter = new ComboBox<>();
		subjectFilter.setPromptText("Filter by Subject");
		subjectFilter.getItems().addAll("All Subjects", "Mathematics", "Computer Science", "English", "Biology", "Physics");
		subjectFilter.setValue("All Subjects");

		Button addButton = new Button("Add Course");
		addButton.setStyle("-fx-background-color: " + PRIMARY_COLOR + "; -fx-text-fill: white;");

		if (!"ADMIN".equals(userRole)) {
			addButton.setDisable(true);
		}

		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);

		controls.getChildren().addAll(searchField, subjectFilter, spacer, addButton);

		// Courses table
		TableView<Course> coursesTable = new TableView<>();

		TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
		codeCol.setPrefWidth(100);

		TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		nameCol.setPrefWidth(200);

		TableColumn<Course, String> subjectCol = new TableColumn<>("Subject");
		subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
		subjectCol.setPrefWidth(150);

		TableColumn<Course, String> sectionCol = new TableColumn<>("Section");
		sectionCol.setCellValueFactory(new PropertyValueFactory<>("section"));
		sectionCol.setPrefWidth(80);

		TableColumn<Course, String> teacherCol = new TableColumn<>("Teacher");
		teacherCol.setCellValueFactory(new PropertyValueFactory<>("teacher"));
		teacherCol.setPrefWidth(150);

		TableColumn<Course, String> capacityCol = new TableColumn<>("Capacity");
		capacityCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		capacityCol.setPrefWidth(80);

		TableColumn<Course, String> locationCol = new TableColumn<>("Location");
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		locationCol.setPrefWidth(100);

		coursesTable.getColumns().addAll(codeCol, nameCol, subjectCol, sectionCol, teacherCol, capacityCol, locationCol);
		coursesTable.getItems().addAll(courseService.getAllCourses());

		// Add action column if ADMIN
		if ("ADMIN".equals(userRole)) {
			coursesTable.getColumns().add(createCourseActionColumn(coursesTable));
		}

		VBox.setVgrow(coursesTable, Priority.ALWAYS);

		allCoursesContent.getChildren().addAll(controls, coursesTable);
		allCoursesTab.setContent(allCoursesContent);

		// Add more tabs based on role
		if ("ADMIN".equals(userRole)) {
			Tab scheduleTab = new Tab("Schedule");
			VBox scheduleContent = new VBox(10);
			scheduleContent.setPadding(new Insets(10));
			Label scheduleLabel = new Label("Course Schedule Management");
			scheduleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
			scheduleContent.getChildren().add(scheduleLabel);
			scheduleTab.setContent(scheduleContent);

			Tab enrollmentsTab = new Tab("Enrollments");
			VBox enrollmentsContent = new VBox(10);
			enrollmentsContent.setPadding(new Insets(10));
			Label enrollmentsLabel = new Label("Course Enrollments Management");
			enrollmentsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
			enrollmentsContent.getChildren().add(enrollmentsLabel);
			enrollmentsTab.setContent(enrollmentsContent);

			tabPane.getTabs().addAll(allCoursesTab, scheduleTab, enrollmentsTab);
		} else {
			// USER role
			Tab myCoursesTab = new Tab("My Courses");
			VBox myCoursesContent = new VBox(10);
			myCoursesContent.setPadding(new Insets(10));
			Label myCoursesLabel = new Label("My Enrolled Courses");
			myCoursesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
			myCoursesContent.getChildren().add(myCoursesLabel);
			myCoursesTab.setContent(myCoursesContent);

			tabPane.getTabs().addAll(allCoursesTab, myCoursesTab);
		}

		// Add Course button functionality
		addButton.setOnAction(e -> {
			Dialog<Course> dialog = createCourseEditDialog(null);
			dialog.showAndWait().ifPresent(newCourse -> {
				courseService.addCourse(newCourse);
				coursesTable.getItems().add(newCourse);
			});
		});

		VBox.setVgrow(tabPane, Priority.ALWAYS);
		content.getChildren().addAll(titleLabel, tabPane);

		return content;
	}

	private static TableColumn<Course, Void> createCourseActionColumn(TableView<Course> table) {
		TableColumn<Course, Void> actionCol = new TableColumn<>("Actions");
		actionCol.setPrefWidth(150);
		actionCol.setCellFactory(param -> new TableCell<Course, Void>() {
			private final Button editBtn = new Button("Edit");
			private final Button deleteBtn = new Button("Delete");
			private final HBox actionButtons = new HBox(5, editBtn, deleteBtn);

			{
				editBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
				deleteBtn.setStyle("-fx-background-color: " + ACCENT_COLOR + "; -fx-text-fill: white;");

				editBtn.setOnAction(event -> {
					Course course = getTableView().getItems().get(getIndex());
					Dialog<Course> dialog = createCourseEditDialog(course);
					dialog.showAndWait().ifPresent(editedCourse -> {
						courseService.updateCourse(editedCourse);
						table.refresh();
					});
				});

				deleteBtn.setOnAction(event -> {
					Course course = getTableView().getItems().get(getIndex());
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Delete Course");
					alert.setHeaderText("Delete Course: " + course.getName());
					alert.setContentText("Are you sure you want to delete this course?");

					alert.showAndWait().ifPresent(response -> {
						if (response == ButtonType.OK) {
							courseService.deleteCourse(course);
							getTableView().getItems().remove(getIndex());
						}
					});
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty) {
					setGraphic(null);
				} else {
					setGraphic(actionButtons);
				}
			}
		});
		return actionCol;
	}

	private static Dialog<Course> createCourseEditDialog(Course existingCourse) {
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
			nameField.setText(existingCourse.getName());
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
					existingCourse.setName(name);
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
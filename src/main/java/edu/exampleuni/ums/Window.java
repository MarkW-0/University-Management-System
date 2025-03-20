package edu.exampleuni.ums;

import edu.exampleuni.ums.services.*;
import javafx.application.*;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;

import java.io.IOException;

//----------------MainApp.java----------------//

public class Window extends Application {
	private User user;
	private StackPane contentPane;
	private Stage stage;
	private final AuthService authService = new AuthService();
	static final SubjectService subjectService = new SubjectService();
	static final CourseService courseService = new CourseService();

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("University Management System");
		this._setScene(createLoginScreen(), 800, 600);
		stage.show();
	}

//----------------MainApp.java----------------//
//----------------LoginScreen.java----------------//

	private StackPane createLoginScreen() {
		LoginScreen loginScreen = new LoginScreen();

		// Login button action
		loginScreen.loginButton.setOnAction(e -> {
			String username = loginScreen.usernameField.getText();
			String password = loginScreen.passwordField.getText();

			this.user = authService.authenticate(username, password);
			if (this.user == null) {
				loginScreen.errorMessage.setVisible(true);
				return;
			}
			this._setScene(createMainApplication(), 1024, 768);
			this.stage.setMaximized(true);
		});

		return loginScreen;
	}

//----------------LoginScreen.java----------------//
//----------------MainApplicationLayout.java----------------//

	private BorderPane createMainApplication() {

		BorderPane mainLayout = new BorderPane();

		//  header
		Header header = new Header();
		header.setUserLabel("Logged in as: " + this.user.getRole());
		header.logoutButton.setOnAction(e -> this._setScene(createLoginScreen(), 800, 600));
		mainLayout.setTop(header);

		// Create left menu
		VBox menuPane;
		if (this.user.getRole().equals("ADMIN")) {
			menuPane = createAdminMenu();
		} else {
			menuPane = createUserMenu();
		}
		var collapsibleMenuPane = new TitledPane("Nav menu", menuPane);
		collapsibleMenuPane.setSkin(new Nav(collapsibleMenuPane));
		collapsibleMenuPane.getStyleClass().add("menuScrollPane");

		mainLayout.setLeft(collapsibleMenuPane);

		// Create content area
		this.contentPane = new StackPane();
		this.contentPane.setPadding(new Insets(20));
		this.contentPane.getStyleClass().add("contentPane");

		mainLayout.setCenter(this.contentPane);

		// Show the dashboard initially
		this.contentPane.getChildren().add(createDashboard());

		return mainLayout;
	}

	// Only add management options for ADMIN
	private VBox createAdminMenu() {

		VBox menu = new VBox();
		menu.setPrefWidth(250);
		menu.getStyleClass().add("navigationMenu");
		// Add all management options for ADMIN
		menu.getChildren().addAll(
				createMenuItem("Dashboard", e -> setContent(createDashboard())),
				createMenuItem("Subject Management", e -> setContent(createSubjectManagement())),
				createMenuItem("Course Management", e -> setContent(createCourseManagement())),
				createMenuItem("Student Management", e -> setContent(createStudentManagement())),
				createMenuItem("Faculty Management", e -> setContent(createFacultyManagement())),
				createMenuItem("Event Management", e -> setContent(createEventManagement()))
		);
		return menu;
	}

	// Show appropriate USER options
	private VBox createUserMenu() {
		VBox menu = new VBox();
		menu.setPrefWidth(250);
		menu.getStyleClass().add("navigationMenu");
		// USER role - limited menu
		menu.getChildren().addAll(
				createMenuItem("Dashboard", e -> setContent(createDashboard())),
				createMenuItem("Course Management", e -> setContent(createCourseManagement())),
				createMenuItem("Event Management", e -> setContent(createEventManagement())),
				createMenuItem("Profile Management", e -> setContent(createProfileManagement()))
		);
		return menu;
	}


//----------------MainApplicationLayout.java----------------//
//----------------ManagementViews.java----------------//

	private VBox createSubjectManagement() {
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
		addButton.getStyleClass().add("addButton");
		if (!this.user.getRole().equals("ADMIN")) {
			addButton.setDisable(true);
		}

		controls.getChildren().addAll(searchField, new Spacer(), addButton);

		// Subjects table
		TableView<Subject> subjectsTable = new TableView<>();
		TableColumn<Subject, String> codeCol = new TableColumn<>("Subject Code");
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
		codeCol.setPrefWidth(150);

		TableColumn<Subject, String> nameCol = new TableColumn<>("Subject Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
		nameCol.setPrefWidth(400);

		subjectsTable.getColumns().addAll(codeCol, nameCol);

		// Add button functionality
		addButton.setOnAction(e -> {
			Dialog<Subject> dialog = createSubjectEditDialog(null);
			dialog.showAndWait().ifPresent(newSubject -> {
				subjectService.addSubject(newSubject);
				subjectsTable.getItems().add(newSubject);
			});
		});

		// Add action column if ADMIN
		if (this.user.getRole().equals("ADMIN")) {
			TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new SubjectManagementAdminActionCell());
			subjectsTable.getColumns().add(actionCol);
		}

		// Add data
		subjectsTable.getItems().addAll(subjectService.getAllSubjects());

		VBox.setVgrow(subjectsTable, Priority.ALWAYS);

		// Add all elements to content
		content.getChildren().addAll(titleLabel, controls, subjectsTable);
		return content;
	}

	static Dialog<Subject> createSubjectEditDialog(Subject existingSubject) {
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

	// Course Management content
	private VBox createCourseManagement() {
		CourseManagement content = new CourseManagement();

		if (!this.user.getRole().equals("ADMIN")) {
			content.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (this.user.getRole().equals("ADMIN")) {
			TableColumn<Course, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new CourseManagementAdminActionCell());
			content.courseTable.getColumns().add(actionCol);
		}
		// Add Course button functionality
		content.addButton.setOnAction(e -> {
			Dialog<Course> dialog = createCourseEditDialog(null);
			dialog.showAndWait().ifPresent(newCourse -> {
				courseService.addCourse(newCourse);
				content.courseTable.getItems().add(newCourse);
			});
		});


		// Add more tabs based on role
		//if (this.user.getRole().equals("ADMIN")) {
		//Tab scheduleTab = new Tab("Schedule");
			/*VBox scheduleContent = new VBox(10);
			scheduleContent.setPadding(new Insets(10));
			Label scheduleLabel = new Label("Course Schedule Management");
			scheduleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
			scheduleContent.getChildren().add(scheduleLabel);
			scheduleTab.setContent(scheduleContent);
			*/
		//Tab enrollmentsTab = new Tab("Enrollments");
			/*VBox enrollmentsContent = new VBox(10);
			enrollmentsContent.setPadding(new Insets(10));
			Label enrollmentsLabel = new Label("Course Enrollments Management");
			enrollmentsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
			enrollmentsContent.getChildren().add(enrollmentsLabel);
			enrollmentsTab.setContent(enrollmentsContent);
			*/

		//tabPane.getTabs().addAll(allCoursesTab, scheduleTab, enrollmentsTab);
		//} else {
		// USER role
		//Tab myCoursesTab = new Tab("My Courses");
			/*VBox myCoursesContent = new VBox(10);
			myCoursesContent.setPadding(new Insets(10));
			Label myCoursesLabel = new Label("My Enrolled Courses");
			myCoursesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
			myCoursesContent.getChildren().add(myCoursesLabel);
			myCoursesTab.setContent(myCoursesContent);
			*/

		//tabPane.getTabs().addAll(allCoursesTab, myCoursesTab);
		//}
		return content;
	}

	private VBox createMenuItem(String name, EventHandler<MouseEvent> action) {
		MenuItem item = new MenuItem();
		item.setName(name);
		// Click action
		item.setOnMouseClicked(action);
		return item;
	}

	// Dashboard content
	private Node createDashboard() {
		return createFXML("Dashboard.fxml");
	}

	// Student Management content
	private Node createStudentManagement() {
		return createFXML("StudentManagement.fxml");
	}

	// Faculty Management content
	private Node createFacultyManagement() {
		return createFXML("FacultyManagement.fxml");
	}

	// Event Management content
	private Node createEventManagement() {
		return createFXML("EventManagement.fxml");
	}

	// Profile Management (for USER role)
	private Node createProfileManagement() {
		return createFXML("ProfileManagement.fxml");
	}


	private Node createFXML(String fxml) {
		try {
			return new FXMLLoader(Window.class.getResource(fxml)).load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	static Dialog<Course> createCourseEditDialog(Course existingCourse) {
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

	//----------------ManagementViews.java----------------//

	private void _setScene(Parent content, double width, double height) {
		Scene scene = new Scene(content, width, height);
		var stylesheet = Window.class.getResource("style.css");
		if (stylesheet != null) scene.getStylesheets().add(stylesheet.toExternalForm());
		else System.err.println("missing stylesheet");
		this.stage.setScene(scene);
	}

	private void setContent(Node content) {
		this.contentPane.getChildren().clear();
		this.contentPane.getChildren().add(content);
	}

	// Main method
	public static void main(String[] args) {
		launch(args);
	}
}
package edu.exampleuni.ums;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Window extends Application {
	private String userRole = "";
	private StackPane contentPane;
	private Stage stage;
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setTitle("University Management System");
		this._setScene(createLoginScreen(), 800, 600);
		stage.show();
	}
	private StackPane createLoginScreen() {
		LoginScreen loginScreen = new LoginScreen();

		// Login button action
		loginScreen.loginButton.setOnAction(e -> {
			// Simple authentication logic for demo purposes
			// needs JDBC

			String username = loginScreen.usernameField.getText();
			String password = loginScreen.passwordField.getText();

			if (username.equals("admin") && password.equals("admin")) {
				this.userRole = "ADMIN";
			} else if (username.equals("user") && password.equals("user")) {
				this.userRole = "USER";
			} else {
				loginScreen.errorMessage.setVisible(true);
				return;
			}
			this._setScene(createMainApplication(), 1024, 768);
			this.stage.setMaximized(true);
		});

		return loginScreen;
	}

	private BorderPane createMainApplication() {

		BorderPane mainLayout = new BorderPane();

		//  header
		Header header = new Header();
		header.setUserLabel("Logged in as: " + this.userRole);
		header.logoutButton.setOnAction(e -> this._setScene(createLoginScreen(), 800, 600));
		mainLayout.setTop(header);

		// Create left menu
		VBox menuPane;
		if (this.userRole.equals("ADMIN")) {
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
		if (this.userRole.equals("USER")) {
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
			dialog.showAndWait().ifPresent(newSubject -> subjectsTable.getItems().add(newSubject));
		});

		// Add action column if ADMIN
		if (this.userRole.equals("ADMIN")) {
			TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new SubjectManagementAdminActionCell());
			subjectsTable.getColumns().add(actionCol);
		}

		// Add sample data
		subjectsTable.getItems().addAll(
				new Subject("MATH001", "Mathematics 101"),
				new Subject("CS101", "Introduction to Computer Science"),
				new Subject("ENG201", "Advanced English Composition"),
				new Subject("BIO101", "Fundamentals of Biology"),
				new Subject("PHYS101", "Physics I")
		);

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
			codeField.setText(existingSubject.code.get());
			nameField.setText(existingSubject.subjectName.get());
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
				// If editing existing subject, create a new Subject with updated values
				return new Subject(code, name);
			}
			return null;
		});
		return dialog;
	}

	// Course Management content
	private VBox createCourseManagement() {
		CourseManagement content = new CourseManagement();

		if (this.userRole.equals("USER")) {
			content.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (this.userRole.equals("ADMIN")) {
			TableColumn<Course, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new CourseManagementAdminActionCell());
			content.courseTable.getColumns().add(actionCol);
		}
		// Add more tabs based on role
		//if (this.userRole.equals("ADMIN")) {
			//Tab scheduleTab = new Tab("Schedule");
			//Tab enrollmentsTab = new Tab("Enrollments");
		//} else {
			// USER role
			//Tab myCoursesTab = new Tab("My Courses");
		//}
		return content;
	}

	private VBox createMenuItem(String name, javafx.event.EventHandler<MouseEvent> action) {
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
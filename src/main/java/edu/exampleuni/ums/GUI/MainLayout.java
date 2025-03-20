package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.*;
import edu.exampleuni.ums.models.Course;
import edu.exampleuni.ums.models.Subject;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.*;

import java.io.IOException;

public class MainLayout extends BorderPane {
	private final StackPane contentPane;
	private final MainApp mainApp;
	public MainLayout(MainApp mainApp) {
		super();
		this.mainApp = mainApp;

		this.setTop(new Header(mainApp));

		// Create left menu
		VBox menuPane;
		if (mainApp.user.getRole().equals("ADMIN")) {
			menuPane = this.createAdminMenu();
		} else {
			menuPane = this.createUserMenu();
		}
		var collapsibleMenuPane = new TitledPane("Nav menu", menuPane);
		collapsibleMenuPane.setSkin(new Nav(collapsibleMenuPane));
		collapsibleMenuPane.getStyleClass().add("menuScrollPane");

		this.setLeft(collapsibleMenuPane);

		// Create content area
		this.contentPane = new StackPane();
		this.contentPane.setPadding(new Insets(20));
		this.contentPane.getStyleClass().add("contentPane");

		this.setCenter(this.contentPane);

		// Show the dashboard initially
		this.contentPane.getChildren().add(this.createDashboard());
	}

	// Only add management options for ADMIN
	private VBox createAdminMenu() {

		VBox menu = new VBox();
		menu.setPrefWidth(250);
		menu.getStyleClass().add("navigationMenu");
		// Add all management options for ADMIN
		menu.getChildren().addAll(
				new edu.exampleuni.ums.GUI.MenuItem("Dashboard", e -> setContent(createDashboard())),
				new edu.exampleuni.ums.GUI.MenuItem("Subject Management", e -> setContent(createSubjectManagement())),
				new edu.exampleuni.ums.GUI.MenuItem("Course Management", e -> setContent(createCourseManagement())),
				new edu.exampleuni.ums.GUI.MenuItem("Student Management", e -> setContent(createStudentManagement())),
				new edu.exampleuni.ums.GUI.MenuItem("Faculty Management", e -> setContent(createFacultyManagement())),
				new edu.exampleuni.ums.GUI.MenuItem("Event Management", e -> setContent(createEventManagement()))
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
				new edu.exampleuni.ums.GUI.MenuItem("Dashboard", e -> setContent(createDashboard())),
				new edu.exampleuni.ums.GUI.MenuItem("Course Management", e -> setContent(createCourseManagement())),
				new edu.exampleuni.ums.GUI.MenuItem("Event Management", e -> setContent(createEventManagement())),
				new MenuItem("Profile Management", e -> setContent(createProfileManagement()))
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
		if (!this.mainApp.user.getRole().equals("ADMIN")) {
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
			Dialog<Subject> dialog = MainApp.createSubjectEditDialog(null);
			dialog.showAndWait().ifPresent(newSubject -> {
				this.mainApp.subjectService.addSubject(newSubject);
				subjectsTable.getItems().add(newSubject);
			});
		});

		// Add action column if ADMIN
		if (this.mainApp.user.getRole().equals("ADMIN")) {
			TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new SubjectManagementAdminActionCell(this.mainApp));
			subjectsTable.getColumns().add(actionCol);
		}

		// Add data
		subjectsTable.getItems().addAll(this.mainApp.subjectService.getAllSubjects());

		VBox.setVgrow(subjectsTable, Priority.ALWAYS);

		// Add all elements to content
		content.getChildren().addAll(titleLabel, controls, subjectsTable);
		return content;
	}

	// Course Management content
	private VBox createCourseManagement() {
		CourseManagement content = new CourseManagement();

		if (!this.mainApp.user.getRole().equals("ADMIN")) {
			content.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (this.mainApp.user.getRole().equals("ADMIN")) {
			TableColumn<Course, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new CourseManagementAdminActionCell(this.mainApp));
			content.courseTable.getColumns().add(actionCol);
		}
		// Add Course button functionality
		content.addButton.setOnAction(e -> {
			Dialog<Course> dialog = MainApp.createCourseEditDialog(null);
			dialog.showAndWait().ifPresent(newCourse -> {
				this.mainApp.courseService.addCourse(newCourse);
				content.courseTable.getItems().add(newCourse);
			});
		});

		// Add data
		content.courseTable.getItems().addAll(this.mainApp.courseService.getAllCourses());


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
			return new FXMLLoader(MainLayout.class.getResource(fxml)).load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private void setContent(Node content) {
		this.contentPane.getChildren().clear();
		this.contentPane.getChildren().add(content);
	}

}

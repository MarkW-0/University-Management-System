package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.*;
import edu.exampleuni.ums.models.Role;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.io.IOException;

public class MainLayout extends BorderPane {
	private final StackPane contentPane = new StackPane();
	private final MainApp mainApp;
	public MainLayout(MainApp mainApp) {
		super();
		this.mainApp = mainApp;

		this.setTop(new Header(mainApp));

		// Create left menu
		VBox menuPane;
		if (mainApp.user.getRole() == Role.ADMIN) {
			menuPane = this.createAdminMenu();
		} else {
			menuPane = this.createUserMenu();
		}
		var collapsibleMenuPane = new TitledPane("Nav menu", menuPane);
		collapsibleMenuPane.setSkin(new Nav(collapsibleMenuPane));
		collapsibleMenuPane.getStyleClass().add("menuScrollPane");

		this.setLeft(collapsibleMenuPane);

		// Create content area
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
				new MenuItem("Dashboard", this.contentPane, createDashboard()),
				new MenuItem("Subject Management", this.contentPane, new SubjectManagement(this.mainApp)),
				new MenuItem("Course Management", this.contentPane, new CourseManagement(this.mainApp)),
				new MenuItem("User Management", this.contentPane, new UserManagement(this.mainApp)),
				new MenuItem("Student Management", this.contentPane, createStudentManagement()),
				new MenuItem("Faculty Management", this.contentPane, createFacultyManagement()),
				new MenuItem("Event Management", this.contentPane, new EventManagement(this.mainApp)),
				new MenuItem("Profile Management", this.contentPane, createProfileManagement())
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
				new MenuItem("Dashboard", this.contentPane, createDashboard()),
				new MenuItem("Course Management", this.contentPane, new CourseManagement(this.mainApp)),
				new MenuItem("Faculty Management", this.contentPane, createFacultyManagement()),
				new MenuItem("Event Management", this.contentPane, new EventManagement(this.mainApp)),
				new MenuItem("Profile Management", this.contentPane, createProfileManagement())
		);
		return menu;
	}

	// Dashboard content
	private Node createDashboard() { return createFXML("Dashboard.fxml"); }

	// Student Management content
	private Node createStudentManagement() {
		return createFXML("StudentManagement.fxml");
	}

	// Faculty Management content
	private Node createFacultyManagement() {
		return createFXML("FacultyManagement.fxml");
	}

	// Profile Management
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
}

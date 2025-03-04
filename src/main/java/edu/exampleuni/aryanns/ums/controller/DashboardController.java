package edu.exampleuni.aryanns.ums.controller;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import edu.exampleuni.aryanns.ums.model.User;

/**
 * DashboardController creates a dashboard scene with role‑based navigation.
 */
public class DashboardController {
    private User loggedInUser;

    public DashboardController(User user) {
        this.loggedInUser = user;
    }

    /**
     * Creates and returns the dashboard Scene.
     */
    public Scene getDashboardScene(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Left navigation pane setup
        VBox navPane = new VBox();
        navPane.setSpacing(10);
        navPane.setPadding(new Insets(10));
        navPane.setStyle("-fx-background-color: #f0f0f0;");

        // Navigation buttons
        Button subjectMgmtButton = new Button("Subject Management");
        Button courseMgmtButton = new Button("Course Management");
        Button logoutButton = new Button("Logout");

        // For simplicity, both ADMIN and USER get access to view the modules.
        navPane.getChildren().addAll(subjectMgmtButton, courseMgmtButton, logoutButton);

        // Main content area where modules will be displayed
        StackPane mainPane = new StackPane();
        Label welcomeLabel = new Label("Welcome " + loggedInUser.getUsername() + " (" + loggedInUser.getRole() + ")");
        mainPane.getChildren().add(welcomeLabel);

        // Load Subject Management Pane when button is clicked
        subjectMgmtButton.setOnAction(e -> {
            SubjectManagementPane subjectPane = new SubjectManagementPane();
            mainPane.getChildren().clear();
            mainPane.getChildren().add(subjectPane.getPane());
        });

        // Load Course Management Pane when button is clicked
        courseMgmtButton.setOnAction(e -> {
            CourseManagementPane coursePane = new CourseManagementPane();
            mainPane.getChildren().clear();
            mainPane.getChildren().add(coursePane.getPane());
        });

        // Logout action: Return to the login scene
        logoutButton.setOnAction(e -> {
            LoginController loginController = new LoginController();
            Scene loginScene = loginController.getLoginScene(primaryStage);
            primaryStage.setScene(loginScene);
        });

        root.setLeft(navPane);
        root.setCenter(mainPane);

        return new Scene(root, 800, 600);
    }
}

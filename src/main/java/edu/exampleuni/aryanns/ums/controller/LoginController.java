package edu.exampleuni.aryanns.ums.controller;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import edu.exampleuni.aryanns.ums.model.User;

import java.util.HashMap;

/**
 * LoginController handles the login screen and authentication.
 */
public class LoginController {

    // Dummy users stored in a HashMap
    private HashMap<String, User> users;

    public LoginController() {
        users = new HashMap<>();
        // Add a couple of users: one ADMIN and one USER
        users.put("admin", new User("admin", "admin123", User.Role.ADMIN));
        users.put("user", new User("user", "user123", User.Role.USER));
    }

    /**
     * Creates and returns the login Scene.
     */
    public Scene getLoginScene(Stage primaryStage) {
        // Create a grid pane for the login form
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        // UI Elements for username and password
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        Button loginButton = new Button("Login");
        Label messageLabel = new Label();

        // Arrange elements on the grid
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(messageLabel, 1, 3);

        // Handle login button click event
        loginButton.setOnAction((ActionEvent event) -> {
            String username = userField.getText();
            String password = passField.getText();
            if (users.containsKey(username)) {
                User user = users.get(username);
                if (user.getPassword().equals(password)) {
                    // On successful login, switch to the dashboard
                    messageLabel.setText("Login successful!");
                    DashboardController dashboardController = new DashboardController(user);
                    Scene dashboardScene = dashboardController.getDashboardScene(primaryStage);
                    primaryStage.setScene(dashboardScene);
                } else {
                    messageLabel.setText("Incorrect password.");
                }
            } else {
                messageLabel.setText("User not found.");
            }
        });

        return new Scene(grid, 400, 250);
    }
}

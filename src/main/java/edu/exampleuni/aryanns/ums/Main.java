package edu.exampleuni.aryanns.ums;

import edu.exampleuni.aryanns.ums.controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class to launch the University Management System application.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Initialize the login screen using LoginController
        LoginController loginController = new LoginController();
        Scene loginScene = loginController.getLoginScene(primaryStage);

        primaryStage.setTitle("University Management System - Phase 1");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

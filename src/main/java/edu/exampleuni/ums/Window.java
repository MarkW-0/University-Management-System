package edu.exampleuni.ums;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class Window extends Application {
    private static final String PRIMARY_COLOR = "#3498db";
    private static final String SECONDARY_COLOR = "#2c3e50";
    private static final String ACCENT_COLOR = "#e74c3c";
    private String userRole = "";
    private VBox menuPane;
    private StackPane contentPane;
    private Stage stage;
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setMinWidth(1024);
        stage.setMinHeight(768);
        stage.setTitle("University Management System");
        stage.setScene(new Scene(createLoginScreen(), 800, 600));
        stage.show();
    }
    private StackPane createLoginScreen() {
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(30));
        loginForm.setMaxWidth(400);
        loginForm.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 0);");

        // Place holder University logo/header
        Label headerLabel = new Label("University Management System");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        headerLabel.setTextFill(Color.web(Window.SECONDARY_COLOR));

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username / Student ID");
        usernameField.setPrefHeight(40);

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(40);

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setPrefHeight(40);
        loginButton.setPrefWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: " + Window.PRIMARY_COLOR + "; -fx-text-fill: white;");

        // Error message form/label
        Label errorMessage = new Label();
        errorMessage.setTextFill(Color.RED);
        errorMessage.setVisible(false);

        // Add all components to form
        loginForm.getChildren().addAll(headerLabel,
                new Label("Username"), usernameField,
                new Label("Password"), passwordField,
                loginButton, errorMessage);

        // Login button action
        loginButton.setOnAction(e -> {
            // Simple authentication logic for demo purposes
            // needs JDBC

            String username = usernameField.getText();
            String password = passwordField.getText();

            if (username.equals("admin") && password.equals("admin")) {
                this.userRole = "ADMIN";
                Scene mainScene = new Scene(createMainApplication(), 1024, 768);
                this.stage.setScene(mainScene);
                this.stage.setMaximized(true);
            } else if (username.equals("user") && password.equals("user")) {
                this.userRole = "USER";
                Scene mainScene = new Scene(createMainApplication(), 1024, 768);
                this.stage.setScene(mainScene);
                this.stage.setMaximized(true);
            } else {
                errorMessage.setText("Invalid username or password!");
                errorMessage.setVisible(true);
            }
        });

        // Center login form
        StackPane rootPane = new StackPane();
        rootPane.setStyle("-fx-background-color: #f5f5f5;");
        rootPane.getChildren().add(loginForm);

        return rootPane;
    }

    private BorderPane createMainApplication() {

        BorderPane mainLayout = new BorderPane();

        //  header
        HBox header = createHeader();
        mainLayout.setTop(header);

        // Create left menu
        this.menuPane = createMenu();
        ScrollPane menuScrollPane = new ScrollPane(this.menuPane);
        menuScrollPane.setFitToWidth(true);
        menuScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuScrollPane.setPrefWidth(250);
        menuScrollPane.setStyle("-fx-background-color: " + Window.SECONDARY_COLOR + ";");

        mainLayout.setLeft(menuScrollPane);

        // Create content area
        this.contentPane = new StackPane();
        this.contentPane.setPadding(new Insets(20));
        this.contentPane.setStyle("-fx-background-color: #f5f5f5;");

        mainLayout.setCenter(this.contentPane);

        // Show the dashboard initially
        this.contentPane.getChildren().add(createDashboard());

        return mainLayout;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10, 15, 10, 15));
        header.setStyle("-fx-background-color: " + Window.PRIMARY_COLOR + ";");
        header.setAlignment(Pos.CENTER_LEFT);

        // App title
        Label title = new Label("University Management System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        title.setTextFill(Color.WHITE);

        // this is Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User info and logout
        Label userLabel = new Label("Logged in as: " + this.userRole);
        userLabel.setTextFill(Color.WHITE);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: " + Window.ACCENT_COLOR + "; -fx-text-fill: white;");
        logoutBtn.setOnAction(e -> this.stage.setScene(new Scene(createLoginScreen(), 800, 600)));

        header.getChildren().addAll(title, spacer, userLabel, new Separator(javafx.geometry.Orientation.VERTICAL), logoutBtn);
        return header;
    }

    private VBox createMenu() {
        VBox menu = new VBox();
        menu.setPrefWidth(250);
        menu.setStyle("-fx-background-color: " + Window.SECONDARY_COLOR + ";");
        // Dashboard menu item
        VBox dashboardItem = createMenuItem("Dashboard", e -> setContent(createDashboard()));
        menu.getChildren().add(dashboardItem);

        // Only add management options for ADMIN, or show appropriate USER options
        if (this.userRole.equals("ADMIN")) {
            // Add all management options for ADMIN
            menu.getChildren().addAll(
                    createMenuItem("Subject Management", e -> setContent(createSubjectManagement())),
                    createMenuItem("Course Management", e -> setContent(createCourseManagement())),
                    createMenuItem("Student Management", e -> setContent(createStudentManagement())),
                    createMenuItem("Faculty Management", e -> setContent(createFacultyManagement())),
                    createMenuItem("Event Management", e -> setContent(createEventManagement()))
            );
        } else {
            // USER role - limited menu
            menu.getChildren().addAll(
                    createMenuItem("Course Management", e -> setContent(createCourseManagement())),
                    createMenuItem("Event Management", e -> setContent(createEventManagement())),
                    createMenuItem("Profile Management", e -> setContent(createProfileManagement()))
            );
        }

        return menu;
    }

    private VBox createMenuItem(String name, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        VBox item = new VBox();
        item.setPadding(new Insets(10, 15, 10, 15));
        item.setStyle("-fx-cursor: hand;");

        Label label = new Label(name);
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(Color.WHITE);

        item.getChildren().add(label);

        // Hover effect
        item.setOnMouseEntered(e ->
                item.setStyle("-fx-background-color: " + Window.PRIMARY_COLOR + "; -fx-cursor: hand;"));

        item.setOnMouseExited(e ->
                item.setStyle("-fx-cursor: hand;"));

        // Click action
        item.setOnMouseClicked(e -> {
            if (action != null) {
                action.handle(new javafx.event.ActionEvent());
            }

            // Highlight selected menu item
            for (javafx.scene.Node node : this.menuPane.getChildren()) {
                node.setStyle("-fx-cursor: hand;");
            }
            item.setStyle("-fx-background-color: " + Window.PRIMARY_COLOR + "; -fx-cursor: hand;");
        });

        return item;
    }

    // Dashboard content
    private VBox createDashboard() {
        VBox dashboardContent = new VBox(20);
        dashboardContent.setPadding(new Insets(20));

        Label titleLabel = new Label("Dashboard");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Dashboard cards for key metrics
        HBox metricsRow = new HBox(20);
        metricsRow.setAlignment(Pos.CENTER);

        metricsRow.getChildren().addAll(
                createMetricCard("Students", "1,250", "#3498db"),
                createMetricCard("Courses", "87", "#e74c3c"),
                createMetricCard("Faculty", "64", "#2ecc71"),
                createMetricCard("Events", "12", "#f39c12")
        );

        // Recent activities section
        VBox recentActivities = new VBox(10);
        Label activitiesLabel = new Label("Recent Activities");
        activitiesLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ListView<String> activitiesList = new ListView<>();
        activitiesList.getItems().addAll(
                "New student registration: John Doe (10 minutes ago)",
                "Course CS101 updated by Dr. Smith (1 hour ago)",
                "Event 'Tech Symposium' created (3 hours ago)",
                "Faculty member Dr. Johnson edited profile (Yesterday)"
        );

        recentActivities.getChildren().addAll(activitiesLabel, activitiesList);
        VBox.setVgrow(activitiesList, Priority.ALWAYS);

        // Upcoming events
        VBox upcomingEvents = new VBox(10);
        Label eventsLabel = new Label("Upcoming Events");
        eventsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        ListView<String> eventsList = new ListView<>();
        eventsList.getItems().addAll(
                "Tech Symposium (March 10, 2025)",
                "Final Exams Begin (April 15, 2025)",
                "Graduation Ceremony (May 20, 2025)"
        );

        upcomingEvents.getChildren().addAll(eventsLabel, eventsList);
        VBox.setVgrow(eventsList, Priority.ALWAYS);

        // Add all components to dashboard
        dashboardContent.getChildren().addAll(titleLabel, metricsRow, recentActivities, upcomingEvents);

        return dashboardContent;
    }

    private VBox createMetricCard(String title, String value, String color) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(20));
        card.setPrefWidth(200);
        card.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0); -fx-background-radius: 5;");

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", 14));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        valueLabel.setTextFill(Color.web(color));

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
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
        // Subjects table
        TableView<Subject> subjectsTable = new TableView<>();
        TableColumn<Subject, String> codeCol = new TableColumn<>("Subject Code");
        codeCol.setCellValueFactory(cellData -> cellData.getValue().code);
        codeCol.setPrefWidth(150);

        TableColumn<Subject, String> nameCol = new TableColumn<>("Subject Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().name);
        nameCol.setPrefWidth(400);

        subjectsTable.getColumns().addAll(codeCol, nameCol);

        Button addButton = new Button("Add Subject");
        addButton.setStyle("-fx-background-color: " + Window.PRIMARY_COLOR + "; -fx-text-fill: white;");

        // Add button functionality
        addButton.setOnAction(e -> {
            Dialog<Subject> dialog = createSubjectEditDialog(null);
            dialog.showAndWait().ifPresent(newSubject -> {
                subjectsTable.getItems().add(newSubject);
            });
        });

        if (this.userRole.equals("USER")) {
            addButton.setDisable(true);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        controls.getChildren().addAll(searchField, spacer, addButton);

        // Add action column if ADMIN
        if (this.userRole.equals("ADMIN")) {
            subjectsTable.getColumns().add(SubjectManagementAdminActionCol());
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

    private TableColumn<Subject, Void> SubjectManagementAdminActionCol() {
        TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox actionButtons = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
                deleteBtn.setStyle("-fx-background-color: " + Window.ACCENT_COLOR + "; -fx-text-fill: white;");

                editBtn.setOnAction(event -> {
                    Subject subject = getTableView().getItems().get(getIndex());
                    Dialog<Subject> dialog = createSubjectEditDialog(subject);
                    dialog.showAndWait().ifPresent(editedSubject -> {
                        // Update the existing subject in the table
                        int index = getTableView().getItems().indexOf(subject);
                        if (index != -1) {
                            getTableView().getItems().set(index, editedSubject);
                        }
                    });
                });

                deleteBtn.setOnAction(event -> {
                    Subject subject = getTableView().getItems().get(getIndex());
                    // Show confirmation dialog
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Subject");
                    alert.setHeaderText("Delete Subject: " + subject.name.get());
                    alert.setContentText("Are you sure you want to delete this subject?");

                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
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
    private Dialog<Subject> createSubjectEditDialog(Subject existingSubject) {
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
        // Pre-fill fields if editing a subject that is already there
        if (existingSubject != null) {
            codeField.setText(existingSubject.code.get());
            nameField.setText(existingSubject.name.get());
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
    private Dialog<Subject> createSubjectForm(Subject subject) {
        // Create a dialog for the subject form
        Dialog<Subject> dialog = new Dialog<>();
        dialog.setTitle(subject == null ? "Add Subject" : "Edit Subject");

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

        if (subject != null) {
            codeField.setText(subject.code.get());
            nameField.setText(subject.name.get());
        }

        grid.add(new Label("Subject Code:"), 0, 0);
        grid.add(codeField, 1, 0);
        grid.add(new Label("Subject Name:"), 0, 1);
        grid.add(nameField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        return dialog;
    }

    // Course Management content
    private VBox createCourseManagement() {
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
        addButton.setStyle("-fx-background-color: " + Window.PRIMARY_COLOR + "; -fx-text-fill: white;");
        if (this.userRole.equals("USER")) {
            addButton.setDisable(true);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        controls.getChildren().addAll(searchField, subjectFilter, spacer, addButton);

        // Courses table
        TableView<Course> coursesTable = new TableView<>();

        TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
        codeCol.setCellValueFactory(cellData -> cellData.getValue().code);
        codeCol.setPrefWidth(100);

        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(cellData -> cellData.getValue().name);
        nameCol.setPrefWidth(200);

        TableColumn<Course, String> subjectCol = new TableColumn<>("Subject");
        subjectCol.setCellValueFactory(cellData -> cellData.getValue().subject);
        subjectCol.setPrefWidth(150);

        TableColumn<Course, String> sectionCol = new TableColumn<>("Section");
        sectionCol.setCellValueFactory(cellData -> cellData.getValue().section);
        sectionCol.setPrefWidth(80);

        TableColumn<Course, String> teacherCol = new TableColumn<>("Teacher");
        teacherCol.setCellValueFactory(cellData -> cellData.getValue().teacher);
        teacherCol.setPrefWidth(150);

        TableColumn<Course, String> capacityCol = new TableColumn<>("Capacity");
        capacityCol.setCellValueFactory(cellData -> cellData.getValue().capacity);
        capacityCol.setPrefWidth(80);

        TableColumn<Course, String> locationCol = new TableColumn<>("Location");
        locationCol.setCellValueFactory(cellData -> cellData.getValue().location);
        locationCol.setPrefWidth(100);

        coursesTable.getColumns().addAll(codeCol, nameCol, subjectCol, sectionCol, teacherCol, capacityCol, locationCol);

        // Add action column if ADMIN
        if (this.userRole.equals("ADMIN")) {
            coursesTable.getColumns().add(CourseManagementAdminActionCol());
        }

        // Add sample data
        coursesTable.getItems().addAll(
                new Course("CS101", "Introduction to Programming", "Computer Science", "Section 1", "Dr. Smith", "30", "Room 101"),
                new Course("MATH101", "Calculus I", "Mathematics", "Section 1", "Dr. Johnson", "35", "Room 202"),
                new Course("ENG201", "Advanced Composition", "English", "Section 2", "Prof. Williams", "25", "Room 303"),
                new Course("BIO101", "Introduction to Biology", "Biology", "Section 1", "Dr. Brown", "40", "Lab 1"),
                new Course("PHYS101", "Physics I", "Physics", "Section 3", "Dr. Davis", "30", "Room 405")
        );

        VBox.setVgrow(coursesTable, Priority.ALWAYS);

        allCoursesContent.getChildren().addAll(controls, coursesTable);
        allCoursesTab.setContent(allCoursesContent);

        // Add more tabs based on role
        if (this.userRole.equals("ADMIN")) {
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

        VBox.setVgrow(tabPane, Priority.ALWAYS);
        content.getChildren().addAll(titleLabel, tabPane);

        return content;
    }

    private static TableColumn<Course, Void> CourseManagementAdminActionCol() {
        TableColumn<Course, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setPrefWidth(150);
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox actionButtons = new HBox(5, editBtn, deleteBtn);

            {
                editBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
                deleteBtn.setStyle("-fx-background-color: " + Window.ACCENT_COLOR + "; -fx-text-fill: white;");
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

    // Student Management content
    private VBox createStudentManagement() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label("Student Management");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Other content would be added here
        // Just a placeholder for now
        content.getChildren().addAll(titleLabel);
        return content;
    }

    // Faculty Management content
    private VBox createFacultyManagement() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label("Faculty Management");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Other content would be added here
        // Just a placeholder for now

        content.getChildren().addAll(titleLabel);
        return content;
    }

    // Event Management content
    private VBox createEventManagement() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        Label titleLabel = new Label("Event Management");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Other content would be added here
        // Just a placeholder for now

        content.getChildren().addAll(titleLabel, new Label("Under construction"));
        return content;
    }
    // Profile Management (for USER role)
    private javafx.scene.Node createProfileManagement() {
        return createFXML("ProfileManagement.fxml");
    }

    private javafx.scene.Node createFXML(String fxml) {
        try {
            return new FXMLLoader(Window.class.getResource(fxml)).load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setContent(javafx.scene.Node content) {
        this.contentPane.getChildren().clear();
        this.contentPane.getChildren().add(content);
    }

    // Subject data model class
    public static class Subject {
        private final javafx.beans.property.StringProperty code;
        private final javafx.beans.property.StringProperty name;

        public Subject(String code, String name) {
            this.code = new javafx.beans.property.SimpleStringProperty(code);
            this.name = new javafx.beans.property.SimpleStringProperty(name);
        }
    }

    public static class Course {
        private final javafx.beans.property.StringProperty code;
        private final javafx.beans.property.StringProperty name;
        private final javafx.beans.property.StringProperty subject;
        private final javafx.beans.property.StringProperty section;
        private final javafx.beans.property.StringProperty teacher;
        private final javafx.beans.property.StringProperty capacity;
        private final javafx.beans.property.StringProperty location;

        public Course(String code, String name, String subject, String section,
                      String teacher, String capacity, String location) {
            this.code = new javafx.beans.property.SimpleStringProperty(code);
            this.name = new javafx.beans.property.SimpleStringProperty(name);
            this.subject = new javafx.beans.property.SimpleStringProperty(subject);
            this.section = new javafx.beans.property.SimpleStringProperty(section);
            this.teacher = new javafx.beans.property.SimpleStringProperty(teacher);
            this.capacity = new javafx.beans.property.SimpleStringProperty(capacity);
            this.location = new javafx.beans.property.SimpleStringProperty(location);
        }
    }

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}
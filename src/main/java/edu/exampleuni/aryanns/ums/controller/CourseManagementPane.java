package edu.exampleuni.aryanns.ums.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import edu.exampleuni.aryanns.ums.model.Course;

/**
 * CourseManagementPane provides a UI to add and view courses.
 */
public class CourseManagementPane {
    // Observable list to hold courses (in-memory storage for Phase 1)
    private ObservableList<Course> courses = FXCollections.observableArrayList();

    /**
     * Returns a Pane containing the Course Management UI.
     */
    public Pane getPane() {
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setPadding(new Insets(10));

        // Title for the module
        Label titleLabel = new Label("Course Management");

        // TableView to display courses
        TableView<Course> table = new TableView<>();
        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourseName()));
        TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
        codeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourseCode()));
        TableColumn<Course, String> subjectCol = new TableColumn<>("Subject Name");
        subjectCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSubjectName()));
        table.getColumns().addAll(nameCol, codeCol, subjectCol);
        table.setItems(courses);

        // Form to add a new course
        HBox form = new HBox();
        form.setSpacing(10);
        TextField courseNameField = new TextField();
        courseNameField.setPromptText("Course Name");
        TextField courseCodeField = new TextField();
        courseCodeField.setPromptText("Course Code");
        TextField subjectNameField = new TextField();
        subjectNameField.setPromptText("Subject Name");
        Button addButton = new Button("Add Course");
        form.getChildren().addAll(courseNameField, courseCodeField, subjectNameField, addButton);

        // Action when clicking the "Add Course" button
        addButton.setOnAction(e -> {
            String courseName = courseNameField.getText().trim();
            String courseCode = courseCodeField.getText().trim();
            String subjectName = subjectNameField.getText().trim();
            if (courseName.isEmpty() || courseCode.isEmpty() || subjectName.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are required.");
                alert.showAndWait();
            } else {
                // Check for duplicate course code
                boolean exists = courses.stream().anyMatch(c -> c.getCourseCode().equals(courseCode));
                if (exists) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Course code already exists.");
                    alert.showAndWait();
                } else {
                    courses.add(new Course(courseName, courseCode, subjectName));
                    courseNameField.clear();
                    courseCodeField.clear();
                    subjectNameField.clear();
                }
            }
        });

        pane.getChildren().addAll(titleLabel, table, form);
        return pane;
    }
}

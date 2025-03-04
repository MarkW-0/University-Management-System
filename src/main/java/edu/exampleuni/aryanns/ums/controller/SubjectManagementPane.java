package edu.exampleuni.aryanns.ums.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import edu.exampleuni.aryanns.ums.model.Subject;

/**
 * SubjectManagementPane provides a UI to add and view subjects.
 */
public class SubjectManagementPane {
    // Observable list to hold subjects (in-memory storage for Phase 1)
    private ObservableList<Subject> subjects = FXCollections.observableArrayList();

    /**
     * Returns a Pane containing the Subject Management UI.
     */
    public Pane getPane() {
        VBox pane = new VBox();
        pane.setSpacing(10);
        pane.setPadding(new Insets(10));

        // Title for the module
        Label titleLabel = new Label("Subject Management");

        // TableView to display subjects
        TableView<Subject> table = new TableView<>();
        TableColumn<Subject, String> nameCol = new TableColumn<>("Subject Name");
        nameCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSubjectName()));
        TableColumn<Subject, String> codeCol = new TableColumn<>("Subject Code");
        codeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getSubjectCode()));
        table.getColumns().addAll(nameCol, codeCol);
        table.setItems(subjects);

        // Form to add a new subject
        HBox form = new HBox();
        form.setSpacing(10);
        TextField nameField = new TextField();
        nameField.setPromptText("Subject Name");
        TextField codeField = new TextField();
        codeField.setPromptText("Subject Code");
        Button addButton = new Button("Add Subject");
        form.getChildren().addAll(nameField, codeField, addButton);

        // Action when clicking the "Add Subject" button
        addButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String code = codeField.getText().trim();
            if (name.isEmpty() || code.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Both fields are required.");
                alert.showAndWait();
            } else {
                // Check for duplicate subject code
                boolean exists = subjects.stream().anyMatch(s -> s.getSubjectCode().equals(code));
                if (exists) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Subject code already exists.");
                    alert.showAndWait();
                } else {
                    subjects.add(new Subject(name, code));
                    nameField.clear();
                    codeField.clear();
                }
            }
        });

        pane.getChildren().addAll(titleLabel, table, form);
        return pane;
    }
}

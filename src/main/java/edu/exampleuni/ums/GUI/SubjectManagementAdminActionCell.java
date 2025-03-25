package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.Subject;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SubjectManagementAdminActionCell extends ActionCell<Subject> {
	SubjectManagementAdminActionCell(MainApp mainApp) {
		super();
		this.editBtn.setOnAction(event -> {
			Subject subject = this.getTableView().getItems().get(getIndex());
			Dialog<Subject> dialog = SubjectManagementAdminActionCell.createEditDialog(subject);
			dialog.showAndWait().ifPresent(editedSubject -> {
				mainApp.subjectService.updateSubject(editedSubject);
				this.getTableView().refresh();
			});
		});

		this.deleteBtn.setOnAction(event -> {
			Subject subject = this.getTableView().getItems().get(getIndex());
			// Show confirmation dialog
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Delete Subject");
			alert.setHeaderText("Delete Subject: " + subject.getSubjectName());
			alert.setContentText("Are you sure you want to delete this subject?");

			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					mainApp.subjectService.deleteSubject(subject);
					this.getTableView().getItems().remove(getIndex());
				}
			});
		});
	}
	public static Dialog<Subject> createEditDialog(Subject existingSubject) { // todo move to own class?
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

		TextField codeField = new TextField();	codeField.setPromptText("Subject Code");
		TextField nameField = new TextField();	nameField.setPromptText("Subject Name");
		// Pre-fill fields if editing a subject that is already there
		if (existingSubject != null) {
			codeField.setText(existingSubject.getCode());
			nameField.setText(existingSubject.getSubjectName());
		}
		grid.add(new Label("Subject Code:"), 0, 0);	grid.add(codeField, 1, 0);
		grid.add(new Label("Subject Name:"), 0, 1);	grid.add(nameField, 1, 1);
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

}
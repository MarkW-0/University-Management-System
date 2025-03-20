package edu.exampleuni.ums;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class SubjectManagementAdminActionCell extends ActionCell<Subject> {
	{
		this.editBtn.setOnAction(event -> {
			Subject subject = this.getTableView().getItems().get(getIndex());
			Dialog<Subject> dialog = Window.createSubjectEditDialog(subject);
			dialog.showAndWait().ifPresent(editedSubject -> {
				Window.subjectService.updateSubject(editedSubject);
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
					Window.subjectService.deleteSubject(subject);
					this.getTableView().getItems().remove(getIndex());
				}
			});
		});
	}
}
package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.Subject;
import javafx.scene.control.*;

public class SubjectManagementAdminActionCell extends ActionCell<Subject> {
	SubjectManagementAdminActionCell(MainApp mainApp) {
		super();
		this.editBtn.setOnAction(event -> {
			Subject subject = this.getTableView().getItems().get(getIndex());
			Dialog<Subject> dialog = MainApp.createSubjectEditDialog(subject);
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
}
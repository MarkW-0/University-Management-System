package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.Subject;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SubjectManagement extends VBox {
	@FXML public TableView<Subject> subjectTable;
	@FXML public Button addButton;

	public SubjectManagement(MainApp mainApp) {
		FXMLLoader fxmlLoader = new FXMLLoader(SubjectManagement.class.getResource("SubjectManagement.fxml"));
		fxmlLoader.setController(this);
		fxmlLoader.setRoot(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!mainApp.userAuth.getRole().equals("ADMIN")) {
			this.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (mainApp.userAuth.getRole().equals("ADMIN")) {
			TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new SubjectManagementAdminActionCell(mainApp));
			this.subjectTable.getColumns().add(actionCol);
		}
		// Add Subject button functionality
		this.addButton.setOnAction(e -> {
			Dialog<Subject> dialog = SubjectManagementAdminActionCell.createEditDialog(null);
			dialog.showAndWait().ifPresent(newSubject -> {
				mainApp.subjectService.addSubject(newSubject);
				this.subjectTable.getItems().add(newSubject);
			});
		});

		// Add data
		this.subjectTable.getItems().addAll(mainApp.subjectService.getAllSubjects());
	}
}
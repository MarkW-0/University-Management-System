package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SubjectManagement extends VBox {
	@FXML public TableView<Subject> table;
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

		if (mainApp.user.getRole() != Role.ADMIN) {
			this.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (mainApp.user.getRole() == Role.ADMIN) {
			TableColumn<Subject, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new SubjectEditActions(mainApp));
			this.table.getColumns().add(actionCol);
		}
		// Add Subject button functionality
		this.addButton.setOnAction(e -> {
			Dialog<Subject> dialog = SubjectEditActions.createEditDialog(null);
			dialog.showAndWait().ifPresent(newSubject -> {
				mainApp.subjectService.add(newSubject);
				this.table.getItems().add(newSubject);
			});
		});

		// Add data
		this.table.getItems().addAll(mainApp.subjectService.getAll());
	}
}
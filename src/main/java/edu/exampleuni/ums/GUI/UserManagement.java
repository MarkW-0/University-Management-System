package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserManagement extends VBox {
	@FXML public TableView<User> table;
	@FXML public Button addButton;

	public UserManagement(MainApp mainApp) {
		FXMLLoader fxmlLoader = new FXMLLoader(UserManagement.class.getResource("UserManagement.fxml"));
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
			TableColumn<User, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new UserEditActions(mainApp));
			this.table.getColumns().add(actionCol);
		}
		// Add User button functionality
		this.addButton.setOnAction(e -> {
			Dialog<User> dialog = UserEditActions.createEditDialog(null);
			dialog.showAndWait().ifPresent(newUser -> {
				mainApp.userService.add(newUser);
				this.table.getItems().add(newUser);
			});
		});
		// Add data
		this.table.getItems().addAll(mainApp.userService.getAll());
	}
}
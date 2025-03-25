package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.User;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserManagement extends VBox {
	@FXML public TableView<User> userTable;
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
		if (!mainApp.userAuth.getRole().equals("ADMIN")) {
			this.addButton.setDisable(true);
		}
		// Add action column if ADMIN
		if (mainApp.userAuth.getRole().equals("ADMIN")) {
			TableColumn<User, Void> actionCol = new TableColumn<>("Actions");
			actionCol.setPrefWidth(150);
			actionCol.setCellFactory(param -> new UserManagementAdminActionCell(mainApp));
			this.userTable.getColumns().add(actionCol);
		}
		// Add User button functionality
		this.addButton.setOnAction(e -> {
			Dialog<User> dialog = UserManagementAdminActionCell.createEditDialog(null);
			dialog.showAndWait().ifPresent(newUser -> {
				mainApp.userService.addUser(newUser);
				this.userTable.getItems().add(newUser);
			});
		});
		// Add data
		this.userTable.getItems().addAll(mainApp.userService.getAllUsers());
	}
}
package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.User;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class UserManagementAdminActionCell extends ActionCell<User> {
	UserManagementAdminActionCell(MainApp mainApp) {
		super();
		this.editBtn.setOnAction(event -> {
			User user = this.getTableView().getItems().get(getIndex());
			Dialog<User> dialog = UserManagementAdminActionCell.createEditDialog(user);
			dialog.showAndWait().ifPresent(editedUser -> {
				mainApp.userService.updateUser(editedUser);
				this.getTableView().refresh();
			});
		});

		this.deleteBtn.setOnAction(event -> {
			User user = this.getTableView().getItems().get(getIndex());
			// Show confirmation dialog
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Delete User");
			alert.setHeaderText("Delete User: " + user.getFullName() + " ( " + user.getID() + " )");
			alert.setContentText("Are you sure you want to delete this user?");

			alert.showAndWait().ifPresent(response -> {
				if (response == ButtonType.OK) {
					mainApp.userService.deleteUser(user);
					this.getTableView().getItems().remove(getIndex());
				}
			});
		});
	}
	public static Dialog<User> createEditDialog(User existingUser) { // todo move to own class?
		Dialog<User> dialog = new Dialog<>();
		dialog.setTitle(existingUser == null ? "Add User" : "Edit User");

		ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField idField = new TextField();	idField.setPromptText("ID");
		TextField fullNameField = new TextField();	fullNameField.setPromptText("Name");
		TextField emailField = new TextField();		emailField.setPromptText("Email");


		// Pre-fill fields if editing an existing user
		if (existingUser != null) {
			idField.setText(existingUser.getID());
			fullNameField.setText(existingUser.getFullName());
			emailField.setText(existingUser.getEmail());
		} else {
			// Default values for new user
			// sectionField.setText("Section 1");
			// capacityField.setText("30");
			// locationField.setText("Room 101");
		}

		grid.add(new Label("ID:"), 0, 0); grid.add(idField,1, 0);
		grid.add(new Label("Name:"),  0, 1); grid.add(fullNameField, 1, 1);
		grid.add(new Label("Email:"),	  0, 2); grid.add(emailField,	 1, 2);

		dialog.getDialogPane().setContent(grid);

		// Convert the result to a User when the save button is clicked
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == saveButtonType) {
				String id = idField.getText().trim();
				String fullName = fullNameField.getText().trim();
				String email = emailField.getText().trim();

				// Validate input
				if (id.isEmpty() || fullName.isEmpty() || email.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid Input");
					alert.setHeaderText(null);
					alert.setContentText("ID, name, and email cannot be empty!");
					alert.showAndWait();
					return null;
				}

				// If editing existing user, update values; otherwise create new User
				User output = existingUser != null ? existingUser : new User();
				output.setID(id);
				output.setFullName(fullName);
				output.setEmail(email);
				return output;
			}
			return null;
		});

		return dialog;
	}

}
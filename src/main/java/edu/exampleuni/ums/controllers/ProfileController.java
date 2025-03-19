package edu.exampleuni.ums.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;

public class ProfileController {
	@FXML
	private Label titleLabel; // Matches the Label with text "Profile Management"

	@FXML
	private Button uploadPhotoBtn; // Matches the "Upload Photo" button

	@FXML
	private PasswordField currentPasswordField; // Matches the current password field

	@FXML
	private PasswordField newPasswordField; // Matches the new password field

	@FXML
	private PasswordField confirmPasswordField; // Matches the confirm password field

	@FXML
	private Button updatePasswordBtn; // Matches the "Update Password" button

	@FXML
	public void initialize() {
		// Initialize the controller (e.g., set up event handlers)
		uploadPhotoBtn.setOnAction(e -> handleUploadPhoto());
		updatePasswordBtn.setOnAction(e -> handleUpdatePassword());
	}

	private void handleUploadPhoto() {
		// Handle upload photo logic
		System.out.println("Upload Photo button clicked!");
	}

	private void handleUpdatePassword() {
		// Handle update password logic
		String currentPassword = currentPasswordField.getText();
		String newPassword = newPasswordField.getText();
		String confirmPassword = confirmPasswordField.getText();

		if (newPassword.equals(confirmPassword)) {
			System.out.println("Password updated successfully!");
		} else {
			System.out.println("New password and confirm password do not match!");
		}
	}
}
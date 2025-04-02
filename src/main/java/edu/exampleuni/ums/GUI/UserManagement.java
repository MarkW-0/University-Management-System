package edu.exampleuni.ums.GUI;

import edu.exampleuni.ums.MainApp;
import edu.exampleuni.ums.models.*;

public class UserManagement extends Management<User> {
	public UserManagement(MainApp mainApp) {
		super(mainApp, mainApp.userService, UserEditActions::new, UserEditActions::createEditDialog, "UserManagement.fxml");
	}
}
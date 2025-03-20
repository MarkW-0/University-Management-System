package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.User;
import javafx.scene.control.*;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class UserService {
	private final List<User> users = new ArrayList<>(Arrays.asList(
			new User("admin", "admin".getBytes(StandardCharsets.UTF_8),	"ADMIN"),
			new User("user", "user".getBytes(StandardCharsets.UTF_8), "USER")
		)
	);

	public List<User> getAllUsers() {
		return new ArrayList<>(users);
	}
	public User authenticate(TextField usernameField, PasswordField passwordField) {
		String username = usernameField.getText();
		byte[] password = passwordField.getText().getBytes(StandardCharsets.UTF_8);
		passwordField.setText("");
		for(User user : users) {
			if(user.getUsername().equals(username)) {
				if (user.login(password)) return user;
			}
		}
		return null;
	}
}
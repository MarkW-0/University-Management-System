package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.User;

public class AuthService {
	public User authenticate(String username, String password) {
		// Replace with database logic
		if ("admin".equals(username) && "admin".equals(password)) {
			return new User(username, password, "ADMIN");
		} else if ("user".equals(username) && "user".equals(password)) {
			return new User(username, password, "USER");
		}
		return null;
	}
}
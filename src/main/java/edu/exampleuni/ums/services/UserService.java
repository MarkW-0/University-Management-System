package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.User;

import java.util.*;

public class UserService {
	private final List<User> users = new ArrayList<>(Arrays.asList(
			new User("admin", "ADMIN", "admin@exampleuni.edu"),
			new User("user", "USER", "user@exampleuni.edu")
		)
	);

	public List<User> getAllUsers() {
		return new ArrayList<>(users);
	}
	
	public void addUser(User user) {
		users.add(user);
	}

	public void deleteUser(User user) {
		users.remove(user);
	}

	public void updateUser(User updatedUser) {
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			if (user.getEmail().equals(updatedUser.getEmail())) {
				users.set(i, updatedUser);
				break;
			}
		}
	}
}
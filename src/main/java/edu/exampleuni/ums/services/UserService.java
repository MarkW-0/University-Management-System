package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Role;
import edu.exampleuni.ums.models.User;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UserService extends Service<User> {
	public UserService() {
		super(new ArrayList<>(Arrays.asList(
				// Updated User instances using the new constructor
				new User("admin", "admin@exampleuni.edu", "admin".getBytes(StandardCharsets.UTF_8), new ArrayList<>(Arrays.asList("Admin")), "ADMIN"),
				new User("faculty", "faculty@exampleuni.edu", "faculty".getBytes(StandardCharsets.UTF_8), new ArrayList<>(Arrays.asList("Faculty")), "FACULTY"),
				new User("student", "student@exampleuni.edu", "student".getBytes(StandardCharsets.UTF_8), new ArrayList<>(Arrays.asList("Student")), "STUDENT"),
				new User("user", "user@exampleuni.edu", "user".getBytes(StandardCharsets.UTF_8), new ArrayList<>(Arrays.asList("User")), "USER")
		)));
	}
}

package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.Role;
import edu.exampleuni.ums.models.User;
import java.nio.charset.*;
import java.util.*;

public class UserService extends Service<User> {
	public UserService() {
		super(new ArrayList<>(Arrays.asList(
				new User(Role.ADMIN, "admin", "admin".getBytes(StandardCharsets.UTF_8), "ADMIN", "admin@exampleuni.edu"),
				new User(Role.FACULTY, "faculty", "faculty".getBytes(StandardCharsets.UTF_8), "FACULTY", "faculty@exampleuni.edu"),
				new User(Role.STUDENT, "student", "student".getBytes(StandardCharsets.UTF_8), "STUDENT", "student@exampleuni.edu"),
				new User(Role.USER, "user", "user".getBytes(StandardCharsets.UTF_8), "USER", "user@exampleuni.edu")
		)));
	}
}
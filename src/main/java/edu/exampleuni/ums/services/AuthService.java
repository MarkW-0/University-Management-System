package edu.exampleuni.ums.services;

import edu.exampleuni.ums.models.UserAuth;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class AuthService {
	private final List<UserAuth> userAuths = new ArrayList<>(Arrays.asList(
			new UserAuth("admin", "admin".getBytes(StandardCharsets.UTF_8), "ADMIN"),
			new UserAuth("user", "user".getBytes(StandardCharsets.UTF_8), "USER")
		)
	);

	public List<UserAuth> getAllUserAuths() {
		return new ArrayList<>(userAuths);
	}
}
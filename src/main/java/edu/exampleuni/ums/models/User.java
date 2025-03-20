package edu.exampleuni.ums.models;

import java.util.*;

public class User {
	private String firstName; // todo
	private String middleNames; // todo
	private String lastName; // todo
	// todo Photo
	private final List<Course> courses = new ArrayList<>(); // todo
	private final List<Event> events = new ArrayList<>(); // todo
	private String email; // todo
	private String username;
	private String password;
	private String role; // ADMIN or USER

	public User(String username, String password, String role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	// Getters and Setters
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
}
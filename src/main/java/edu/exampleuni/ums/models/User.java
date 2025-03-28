package edu.exampleuni.ums.models;

import javafx.beans.property.*;

public class User {
	private final StringProperty id = new SimpleStringProperty("");
	private final StringProperty fullName = new SimpleStringProperty("");
	private final StringProperty email = new SimpleStringProperty("");
	// todo Photo
	//private final List<Course> courses = new ArrayList<>(); // todo
	//private final List<Event> events = new ArrayList<>(); // todo

	public User() {}

	public User(String id, String fullName, String email) {
		this.id.set(id);
		this.fullName.set(fullName);
		this.email.set(email);
	}
	public String getID() { return id.get(); } public void setID(String id) { this.id.set(id); }
	public String getFullName() { return fullName.get(); } public void setFullName(String fullName) { this.fullName.set(fullName); }
	public String getEmail() { return email.get(); } public void setEmail(String email) { this.email.set(email); }

}
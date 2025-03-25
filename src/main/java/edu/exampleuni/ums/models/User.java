package edu.exampleuni.ums.models;

import javafx.beans.property.*;

public class User {
	private final StringProperty firstName;
	//private String middleNames; // todo
	private final StringProperty lastName;
	private final StringProperty email;
	// todo Photo
	//private final List<Course> courses = new ArrayList<>(); // todo
	//private final List<Event> events = new ArrayList<>(); // todo

	public User(String firstName, String lastName, String email) {
		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.email = new SimpleStringProperty(email);
	}
	public String getFirstName() { return firstName.get(); } public void setFirstName(String firstName) { this.firstName.set(firstName); }
	public String getLastName() { return lastName.get(); } public void setLastName(String lastName) { this.lastName.set(lastName); }
	public String getEmail() { return email.get(); } public void setEmail(String email) { this.email.set(email); }

}
package edu.exampleuni.ums.models;


public class Faculty extends User {
	/*	todo
		Degree (e.g., Ph.D., Master's).
		Research Interest
		Office
	*/
	public Faculty(String id, String fullName, String email) {
		super();
		this.setRole(Role.FACULTY);
		this.setID(id);
		this.setFullName(fullName);
		this.setEmail(email);
	}
}
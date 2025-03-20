package edu.exampleuni.ums.models;


public class Faculty extends User {
	/*	todo
		Degree (e.g., Ph.D., Master's).
		Research Interest
		Office
	*/
	public Faculty(String username, byte[] password, String role) {
		super(username, password, role);
	}
}
package edu.exampleuni.ums.models;

import javafx.beans.property.*;
import java.nio.charset.*;
import java.security.*;
import java.util.*;

public class User  extends Model {
	private final StringProperty id = new SimpleStringProperty("");
	private final StringProperty fullName = new SimpleStringProperty("");
	private final StringProperty email = new SimpleStringProperty("");

	private byte[] passwordSalt;
	private byte[] passwordHash;
	private Role role = Role.USER;

	// todo Photo
	//private final List<Course> courses = new ArrayList<>(); // todo
	//private final List<Event> events = new ArrayList<>(); // todo


	public User() {
		this.setPassword("password".getBytes(StandardCharsets.UTF_8));
	}

	public User(Role role, String id, byte[] password, String fullName, String email) {
		this.role = role;
		this.id.set(id);
		this.fullName.set(fullName);
		this.email.set(email);
		this.setPassword(password);
	}

	@Override
	public boolean isEqual(Model updated) {
		if (!(updated instanceof User updatedUser)) return false;
		return this.getID().equals(updatedUser.getID());
	}

	private byte[] newSalt() {
		byte[] random = new byte[16];
		new SecureRandom().nextBytes(random);
		return random;
	}

	private byte[] hash(byte[] password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(this.passwordSalt);
			return md.digest(password);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	public boolean login(byte[] password) {
		return Arrays.equals(hash(password), this.passwordHash);
	}

	public String getID() { return id.get(); } public void setID(String id) { this.id.set(id); }
	public String getFullName() { return fullName.get(); } public void setFullName(String fullName) { this.fullName.set(fullName); }
	public String getEmail() { return email.get(); } public void setEmail(String email) { this.email.set(email); }
	public Role getRole() { return role; } public void setRole(Role role) { this.role = role; }
	public void setPassword(byte[] password) {
		this.passwordSalt = newSalt();
		this.passwordHash = hash(password);
	}
}
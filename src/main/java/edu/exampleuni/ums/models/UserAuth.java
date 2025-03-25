package edu.exampleuni.ums.models;

import java.security.*;
import java.util.*;

public class UserAuth {
	private String username;
	private byte[] passwordSalt;
	private byte[] passwordHash;
	private String role; // ADMIN or USER

	public UserAuth(String username, byte[] password, String role) {
		this.username = username;
		this.passwordSalt = newSalt();
		this.passwordHash = hash(password);
		this.role = role;
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

	// Getters and Setters
	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public void setPassword(byte[] password) {
		this.passwordSalt = newSalt();
		this.passwordHash = hash(password);
	}

	public String getRole() { return role; }
	public void setRole(String role) { this.role = role; }
}
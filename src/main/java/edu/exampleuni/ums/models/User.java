// Old version
/*package edu.exampleuni.ums.models;

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
}*/

package edu.exampleuni.ums.models;
import java.security.*;
import java.util.*;
public class User {
    // Derivative classes just add the required attributes for now, hypothetically they could have unique methods, but that is not implemented as of now.
    protected String userRole;
    private String username;
    // Name stored as an array, accounting for people with middle names
    private ArrayList<String> fullName;
    private String email;
    private byte[] passwordSalt;
    private byte[] passwordHash;
    private Role role;

    // If I'm correct setting newSalt and hash to private will only allow calling them through the setPassword method and login method
    // Generate random salt with  SecureRandom
    private byte[] newSalt() {
        byte[] random = new byte[16];
        new SecureRandom().nextBytes(random);
        return random;
    }

    // Use MessageDigest (Using SHA-512) to create md, add the unique salt, and return the hashed password
    private byte[] hash(byte[] password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(this.passwordSalt);
            return md.digest(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Check if the hash of the password hash matches the password hash of the user
    public boolean login(byte[] password) {
        return Arrays.equals(hash(password), this.passwordHash);
    }

    // Password must be passed as a byte array (this is true for all derivative classes)
    // This can be done through: byte[] passwordBytes = passwordString.getBytes(StandardCharsets.UTF_8); Keep which Charset is being used consistent
    // Where passwordString is the password in string form
    // Pass fullName as an ArrayList
    public User(String name, String email, byte[] password, ArrayList<String> fullName){
        this.username = name;
        this.email = email;
        setPassword(password);
	
    }


    public String getEmail() {
        return email;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUserame() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    // Set a new password, used during construction and when changing password
    public void setPassword(byte[] password) {
        this.passwordSalt = newSalt();
        this.passwordHash = hash(password);
    }
}

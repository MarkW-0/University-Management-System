package edu.exampleuni.aryanns.ums.model;

/**
 * The User class represents a user of the system.
 * It contains basic attributes such as username, password, and role.
 */
public class User {
    private String username;
    private String password;
    private Role role;

    // Enum to define user roles
    public enum Role {
        ADMIN, USER
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}

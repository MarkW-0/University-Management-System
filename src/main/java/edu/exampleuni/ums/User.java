package edu.exampleuni.ums;

public class User {
    protected String userRole;
    private String name;
    private String email;
    private String password;

    // Tests to see if username and password match particular value
    public boolean verifyUsername(String testUsername){
        return name.equals(testUsername);
    }
    public boolean verifyPassword(String testPassword){
        return password.equals(testPassword);
    }
    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getName() {
        return name;
    }

    public void editPassword(String newPassword){
        this.password = newPassword;
    }
}

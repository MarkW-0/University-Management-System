package edu.exampleuni.ums;
import java.security.*;
import java.util.*;
public class User {
    protected String userRole;
    private String name;
    private String email;
    private byte[] passwordSalt;
    private byte[] passwordHash;


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

    public User(String name, String email, byte[] password){
        this.name = name;
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
        return name;
    }

    public void setUsername(String username) {
        this.name = username;
    }
    public void setPassword(byte[] password) {
        this.passwordSalt = newSalt();
        this.passwordHash = hash(password);
    }
}

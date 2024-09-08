package coe528.project;

/**
 * Overview: The User class represents a generic user in the banking system, serving as a superclass
 * for different types of users such as customers and managers.
 */
public abstract class User {
    protected String username;
    protected String password;
    protected String role;
    
    /**
     * Constructs a User with the given username, password, and role.
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
    
    /**
     * Checks if the provided username and password match this user's credentials.
     */
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

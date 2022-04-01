package com.absoft.springsample.entities;

/**
 * User is the representation of a user
 * 
 * @author Abhishek Jha
 */
public class User {

    /**
     * The unique Id for the user
     */
    private int id;
    /**
     * Name of the user
     */
    private String name;
    /**
     * Email of the user
     */
    private String email;

    public User() {
    }

    /**
     * <p>
     * Parameterized constructor for User
     * <p>
     * 
     * @param id    unique id for the user
     * @param name  name of the user
     * @param email email of the user
     */
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

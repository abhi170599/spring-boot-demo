package com.absoft.springsample.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.lang.NonNull;

/**
 * User is the representation of a user
 * 
 * @author Abhishek Jha
 */
public class User {

    private int id;

    @Size(min = 2, message = "Name must have atleast 2 characters")
    private String name;

    @NonNull
    @Email
    private String email;

    public User() {
    }

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

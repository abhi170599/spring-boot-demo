package com.absoft.springsample.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Post is a representation of post created by user
 * 
 * @author Abhishek Jha
 */
@Entity
public class Post {

    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Post() {
    }

    public Post(int id, User user, String description) {
        this.id = id;
        this.user = user;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

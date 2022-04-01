package com.absoft.springsample.entities;

/**
 * Post is a representation of post created by user
 * 
 * @author Abhishek Jha
 */
public class Post {

    private int id;

    private String description;

    private int userId;

    public Post() {
    }

    public Post(int id, int userId, String description) {
        this.id = id;
        this.userId = userId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

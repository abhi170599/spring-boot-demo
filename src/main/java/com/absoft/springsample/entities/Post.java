package com.absoft.springsample.entities;

/**
 * Post is a representation of post created by user
 * 
 * @author Abhishek Jha
 */
public class Post {
    /**
     * Unique Id of the Post
     */
    private int id;
    /**
     * Description of the post
     */
    private String description;
    /**
     * user id of the user who created this post
     */
    private int userId;

    public Post() {
    }

    /**
     * <p>
     * Parameterized constructor for Post
     * </p>
     * 
     * @param id          unique id of the post
     * @param description description text of the post
     */
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

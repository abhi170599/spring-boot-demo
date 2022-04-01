package com.absoft.springsample.entities;

/**
 * HelloWorld is used as a greeting message object
 * 
 * @author Abhishek Jha
 */
public class HelloWorld {
    /**
     * The greeting message
     */
    private String message;

    public HelloWorld() {
    }

    /**
     * <p>
     * Parameterized constructor for HelloWorld
     * </p>
     * 
     * @param message the message to be used as greeting
     */
    public HelloWorld(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

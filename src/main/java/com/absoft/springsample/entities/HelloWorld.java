package com.absoft.springsample.entities;

/**
 * HelloWorld is used as a greeting message object
 * 
 * @author Abhishek Jha
 */
public class HelloWorld {

    private String message;

    public HelloWorld() {
    }

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

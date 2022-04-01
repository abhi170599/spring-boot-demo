package com.absoft.springsample.controllers;

import com.absoft.springsample.entities.HelloWorld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorldController handles http requests
 * directed to /hello route
 * 
 * @author Abhishek Jha
 */
@RestController
public class HelloWorldController {

    /**
     * <p>
     * greet responds with a "Hello World" message
     * </p>
     * 
     * @return greeting message
     */
    @GetMapping(value = "/hello")
    public String greet() {
        return "Hello, World";
    }

    /**
     * <p>
     * customGreets creates a custom message with
     * the provided name
     * </p>
     * 
     * @param name the name to be used in the message
     * @return customized greeting message
     */
    @GetMapping(value = "/hello/{name}")
    public String customGreet(@PathVariable String name) {
        return "Hello, " + name;
    }

    /**
     * <p>
     * greetBean creates a message object with defualt message
     * </p>
     * 
     * @return message object with default message
     */
    @GetMapping(value = "/hellobean")
    public HelloWorld greetBean() {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.setMessage("Hello, World!");
        return helloWorld;
    }

    /**
     * <p>
     * greetBeanWithName creates a message object customized
     * with the provide name
     * <p>
     * 
     * @param name the name to be used in the message
     * @return message object with customized message
     */
    @GetMapping(value = "/hellobean/{name}")
    public HelloWorld greetBeanWithName(@PathVariable String name) {
        return new HelloWorld(String.format("Hello, %s", name));
    }
}

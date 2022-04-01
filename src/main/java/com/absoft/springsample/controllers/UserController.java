package com.absoft.springsample.controllers;

import java.util.List;

import com.absoft.springsample.entities.User;
import com.absoft.springsample.exceptions.NotFoundException;
import com.absoft.springsample.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User user = userRepo.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User not found, id = %s", id));
        }
        return user;
    }

    @DeleteMapping(path = "/users/{id}")
    public User deleteUser(@PathVariable int id) {
        User user = userRepo.deleteById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User not found, id = %s", id));
        }
        return user;
    }

    @PostMapping(path = "/users")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }
}

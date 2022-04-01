package com.absoft.springsample.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.absoft.springsample.entities.User;
import com.absoft.springsample.exceptions.NotFoundException;
import com.absoft.springsample.repositories.UserRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping(path = "/users/{id}")
    public EntityModel<? extends Object> retrieveUser(@PathVariable int id) {
        User user = userRepo.findById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User not found, id = %s", id));
        }

        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        resource.add(linkTo.withRel("all-users"));

        return resource;
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
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepo.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}

package com.absoft.springsample.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.absoft.springsample.entities.User;
import com.absoft.springsample.exceptions.NotFoundException;
import com.absoft.springsample.repositories.UserJpaRepository;
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

    @Autowired
    private UserJpaRepository userJpaRepo;

    @GetMapping(path = "/v1/users")
    public List<User> retrieveAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping(path = "/v2/users")
    public List<User> retrieveAllUsersV2() {
        List<User> users = userJpaRepo.findAll();
        return users;
    }

    @GetMapping(path = "/v1/users/{id}")
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

    @GetMapping(path = "/v2/users/{id}")
    public EntityModel<? extends Object> retrieveUserV2(@PathVariable int id) {
        Optional<User> user = userJpaRepo.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException(String.format("User not found, id = %s", id));
        }

        EntityModel<User> resource = EntityModel.of(user.get());
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsersV2());
        resource.add(linkTo.withRel("all-users"));

        return resource;
    }

    @DeleteMapping(path = "/v1/users/{id}")
    public User deleteUser(@PathVariable int id) {
        User user = userRepo.deleteById(id);
        if (user == null) {
            throw new NotFoundException(String.format("User not found, id = %s", id));
        }
        return user;
    }

    @DeleteMapping(path = "/v2/users/{id}")
    public User deleteUserV2(@PathVariable int id) {
        User user = userJpaRepo.findById(id).get();
        if (user == null) {
            throw new NotFoundException(String.format("User not found, id = %s", id));
        }
        userJpaRepo.deleteById(id);
        return user;
    }

    @PostMapping(path = "/v1/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        User savedUser = userRepo.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/v2/users")
    public ResponseEntity<Object> createUserV2(@Valid @RequestBody User user) {
        User savedUser = userJpaRepo.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();

        return ResponseEntity.created(location).build();
    }
}

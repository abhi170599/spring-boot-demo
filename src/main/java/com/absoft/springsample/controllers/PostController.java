package com.absoft.springsample.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.absoft.springsample.entities.Post;
import com.absoft.springsample.exceptions.NotFoundException;
import com.absoft.springsample.repositories.PostRepository;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepo;

    @GetMapping(path = "/posts")
    public MappingJacksonValue retrieveAllPosts() {
        List<Post> posts = postRepo.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("userId", "description");
        FilterProvider filters = new SimpleFilterProvider().addFilter("PostIdFilter", filter);
        MappingJacksonValue mapping = new MappingJacksonValue(posts);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping(path = "/posts/{id}")
    public EntityModel<? extends Object> retrievePost(@PathVariable int id) {
        Post post = postRepo.findOne(id);
        if (post == null) {
            throw new NotFoundException(String.format("Post not found id = %s", id));
        }
        EntityModel<Post> resource = EntityModel.of(post);
        WebMvcLinkBuilder linkToAll = linkTo(methodOn(this.getClass()).retrieveAllPosts());
        WebMvcLinkBuilder linkToUserPosts = linkTo(methodOn(this.getClass()).retrieveUserPosts(post.getId()));

        resource.add(linkToAll.withRel("all-posts"));
        resource.add(linkToUserPosts.withRel("user-posts"));

        return resource;
    }

    @PostMapping(path = "/posts")
    public ResponseEntity<Object> createPost(@Valid @RequestBody Post post) {
        Post savedPost = postRepo.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/posts/user/{userId}")
    public List<Post> retrieveUserPosts(@PathVariable int userId) {
        return postRepo.findAllByUser(userId);
    }
}

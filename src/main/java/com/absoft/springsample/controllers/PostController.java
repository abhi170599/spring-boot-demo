package com.absoft.springsample.controllers;

import java.util.List;

import com.absoft.springsample.entities.Post;
import com.absoft.springsample.exceptions.NotFoundException;
import com.absoft.springsample.repositories.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    @Autowired
    private PostRepository postRepo;

    @GetMapping(path = "/posts")
    public List<Post> retrieveAllPosts() {
        return postRepo.findAll();
    }

    @GetMapping(path = "/posts/{id}")
    public Post retrievePost(@PathVariable int id) {
        Post post = postRepo.findOne(id);
        if (post == null) {
            throw new NotFoundException(String.format("Post not found id = %s", id));
        }
        return post;
    }

    @PostMapping(path = "/posts")
    public Post createPost(@RequestBody Post post) {
        return postRepo.save(post);
    }

    @GetMapping(path = "/posts/user/{userId}")
    public List<Post> retrieveUserPosts(@PathVariable int userId) {
        return postRepo.findAllByUser(userId);
    }
}

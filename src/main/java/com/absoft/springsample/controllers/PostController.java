package com.absoft.springsample.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.absoft.springsample.entities.Post;
import com.absoft.springsample.entities.User;
import com.absoft.springsample.exceptions.NotFoundException;
import com.absoft.springsample.repositories.PostJpaRepository;
import com.absoft.springsample.repositories.PostRepository;
import com.absoft.springsample.repositories.UserJpaRepository;
import com.absoft.springsample.repositories.UserRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;

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

    @Autowired
    private PostJpaRepository postJpaRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserJpaRepository userJpaRepo;

    @GetMapping(path = "/v1/posts")
    public List<Post> retrieveAllPosts() {
        List<Post> posts = postRepo.findAll();
        return posts;
    }

    @GetMapping(path = "/v2/posts")
    public List<Post> retrieveAllPostsV2() {
        List<Post> posts = postJpaRepo.findAll();
        return posts;
    }

    @GetMapping(path = "/v1/posts/{id}")
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

    @GetMapping(path = "/v2/posts/{id}")
    public EntityModel<? extends Object> retrievePostV2(@PathVariable int id) {
        Optional<Post> post = postJpaRepo.findById(id);
        if (!post.isPresent()) {
            throw new NotFoundException(String.format("Post not found id = %s", id));
        }
        EntityModel<Post> resource = EntityModel.of(post.get());
        WebMvcLinkBuilder linkToAll = linkTo(methodOn(this.getClass()).retrieveAllPostsV2());
        WebMvcLinkBuilder linkToUserPosts = linkTo(methodOn(this.getClass()).retrieveUserPostsV2(post.get().getId()));

        resource.add(linkToAll.withRel("all-posts"));
        resource.add(linkToUserPosts.withRel("user-posts"));

        return resource;
    }

    @PostMapping(path = "/v1/posts")
    public ResponseEntity<Object> createPost(@Valid @RequestBody Post post) {
        Post savedPost = postRepo.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/v2/posts/{userId}")
    public ResponseEntity<Object> createPost(@Valid @RequestBody Post post, @PathVariable int userId) {
        Optional<User> user = userJpaRepo.findById(userId);
        if (!user.isPresent()) {
            throw new NotFoundException(String.format("User not found id = %s", userId));
        }
        post.setUser(user.get());
        Post savedPost = postJpaRepo.save(post);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/v1/posts/user/{userId}")
    public List<Post> retrieveUserPosts(@PathVariable int userId) {
        return postRepo.findAllByUser(userId);
    }

    @GetMapping(path = "/v2/posts/user/{userId}")
    public List<Post> retrieveUserPostsV2(@PathVariable int userId) {
        Optional<User> user = userJpaRepo.findById(userId);
        if (!user.isPresent()) {
            throw new NotFoundException(String.format("User not found id = %s", userId));
        }

        return user.get().getPosts();
    }
}

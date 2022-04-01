package com.absoft.springsample.repositories;

import java.util.ArrayList;
import java.util.List;

import com.absoft.springsample.entities.Post;

import org.springframework.stereotype.Component;

@Component
public class PostRepository {

    private static List<Post> posts = new ArrayList<>();
    private static int postCounter = 3;

    /*
     * Initialize dummy data
     */
    static {
        posts.add(new Post(1, 1, "sample post 1"));
        posts.add(new Post(2, 1, "sample post 2"));
    }

    public List<Post> findAll() {
        return posts;
    }

    public Post findOne(int id) {
        for (Post post : posts) {
            if (post.getId() == id)
                return post;
        }
        return null;
    }

    public List<Post> findAllByUser(int userId) {
        List<Post> userPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getUserId() == userId)
                userPosts.add(post);
        }
        return userPosts;
    }

    public Post save(Post post) {
        if (post.getId() == 0)
            post.setId(postCounter++);
        posts.add(post);
        return post;
    }
}

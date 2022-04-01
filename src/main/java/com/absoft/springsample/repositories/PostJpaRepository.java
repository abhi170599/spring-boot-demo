package com.absoft.springsample.repositories;

import com.absoft.springsample.entities.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Integer> {

}

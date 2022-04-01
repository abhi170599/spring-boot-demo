package com.absoft.springsample.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.absoft.springsample.entities.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Integer> {

}

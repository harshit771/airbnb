package com.practice.spring.airbnb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.spring.airbnb.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{

    Optional<User> findByEmail(String email);

}

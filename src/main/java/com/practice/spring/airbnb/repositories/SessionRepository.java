package com.practice.spring.airbnb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.spring.airbnb.entities.Session;
import com.practice.spring.airbnb.entities.User;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long>{

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}

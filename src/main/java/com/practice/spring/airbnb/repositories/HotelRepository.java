package com.practice.spring.airbnb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.User;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByOwner(User currentUser);

}

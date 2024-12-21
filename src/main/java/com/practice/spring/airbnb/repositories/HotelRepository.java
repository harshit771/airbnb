package com.practice.spring.airbnb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.spring.airbnb.entities.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,Long>{

}

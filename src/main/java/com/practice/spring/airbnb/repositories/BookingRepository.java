package com.practice.spring.airbnb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.spring.airbnb.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long>{

}

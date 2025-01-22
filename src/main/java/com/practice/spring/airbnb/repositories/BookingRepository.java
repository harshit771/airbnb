package com.practice.spring.airbnb.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.spring.airbnb.entities.Booking;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.User;

public interface BookingRepository extends JpaRepository<Booking,Long>{

    Optional<Booking> findByPaymentSessionId(String sessionId);

    List<Booking> findByHotel(Hotel hotel);

    List<Booking> findByHotelAndCreatedAtBetween(Hotel hotel,LocalDateTime startDateTime, LocalDateTime endDateTime);

    List<Booking> getByUser(User user);

}

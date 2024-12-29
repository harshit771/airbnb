package com.practice.spring.airbnb.dto;

import java.util.Set;

import com.practice.spring.airbnb.entities.Booking;
import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.entities.enums.Gender;

import lombok.Data;

@Data
public class GuestDto {

    
    private User user;


    private String name;

    private Gender gender;

    private Integer age;


    private Set<Booking> bookings;
}

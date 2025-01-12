package com.practice.spring.airbnb.dto;

import java.util.Set;

import com.practice.spring.airbnb.customanotations.EnumValue;
import com.practice.spring.airbnb.entities.Booking;
import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.entities.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GuestDto {

    
    private User user;

    @NotBlank(message = "Guest name cannot be blank" )
    private String name;

    @NotNull(message = "Guest gender cannot be blank" )
    //@EnumValue
    private Gender gender;

    @NotNull(message = "Age cannot be blank")
    private Integer age;


    private Set<Booking> bookings;
}

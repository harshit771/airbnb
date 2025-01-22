package com.practice.spring.airbnb.dto;

import java.time.LocalDate;

import com.practice.spring.airbnb.entities.enums.Gender;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;
}

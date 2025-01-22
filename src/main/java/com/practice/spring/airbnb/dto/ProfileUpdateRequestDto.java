package com.practice.spring.airbnb.dto;

import java.time.LocalDate;

import com.practice.spring.airbnb.entities.enums.Gender;

import lombok.Data;

@Data
public class ProfileUpdateRequestDto {

    private String name;
    private LocalDate dateOfBirth;
    private Gender gender;

}

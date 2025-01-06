package com.practice.spring.airbnb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequestDto {

    @Email(message = "Invalid email id")
    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotBlank(message = "Name cannot be null")
    private String name;
    

}

package com.practice.spring.airbnb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email id")
    private String email;
    @NotBlank(message = "Password cannot be blank")
    private String password;

}

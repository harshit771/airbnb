package com.practice.spring.airbnb.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class HotelContactInfo {

    @NotBlank(message = "Hotel address can not be empty")
    private String address;
    @NotBlank(message = "Hotel phonenumber can not be empty")
    @Pattern(regexp="^[0-9]{10}" ,message="Invalid phone number")
    private String phoneNumber;

    @Email(message = "Invalid email id")
    private String email;

    @NotBlank(message = "location can not be empty")
    private String location;

}

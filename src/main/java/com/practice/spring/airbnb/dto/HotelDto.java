package com.practice.spring.airbnb.dto;
import com.practice.spring.airbnb.entities.HotelContactInfo;
import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Data
public class HotelDto {

    private Long id;

    @NotBlank(message = "Hotel name can not be empty")
    private String name;

    @NotBlank(message = "Hotel city can not be empty")
    private String city;

    @NotEmpty(message = "Hotel photos can not be empty")
    private String[] photos;

    @NotEmpty(message = "Hotel amanities can not be empty")
    private String[] amanities;

    @Valid
    private HotelContactInfo contactInfo;

    private boolean active;

}

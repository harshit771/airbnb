package com.practice.spring.airbnb.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class HotelSearchRequest {

    @NotBlank(message = "Hotel city can not be null")
    private String city;

    @NotBlank(message = "Start date can not be null")
    private LocalDate startDate;

    @NotBlank(message = "End date can not be null")
    private LocalDate endDate;

    @NotBlank(message = "Room counts can not be null")
    private Integer roomsCount;
    
    private Integer page=0;
    private Integer size=10;

}

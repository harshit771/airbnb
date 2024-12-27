package com.practice.spring.airbnb.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class HotelSearchRequest {

    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;
    private Integer page=0;
    private Integer size=10;

}

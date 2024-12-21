package com.practice.spring.airbnb.dto;
import com.practice.spring.airbnb.entities.HotelContactInfo;
import lombok.Data;

@Data
public class HotelDto {

    private Long id;

    private String name;

    private String city;

    private String[] photos;

 
    private String[] amanities;

    private HotelContactInfo contactInfo;

    private boolean active;

}

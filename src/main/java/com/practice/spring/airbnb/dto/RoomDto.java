package com.practice.spring.airbnb.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RoomDto {


    private Long id;

    private String type;

    private BigDecimal price;

    private String[] photos;

    private String[] amanities;


    private Integer totalCount;


    private Integer capacity;

}

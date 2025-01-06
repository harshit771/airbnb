package com.practice.spring.airbnb.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomDto {


    private Long id;

    @NotBlank(message = "Room type cannot be blank")
    private String type;

    @NotBlank(message = "Room price cannot be blank" )
    private BigDecimal price;

    @NotBlank(message = "Room photos cannot be blank" )
    private String[] photos;

    @NotBlank(message = "Room aminities cannot be blank" )
    private String[] amanities;


    @NotBlank(message = "Room totalcount cannot be blank" )
    private Integer totalCount;

    @NotBlank(message = "Room capacity cannot be blank" )
    private Integer capacity;

}

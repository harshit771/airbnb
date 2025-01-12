package com.practice.spring.airbnb.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RoomDto {


    private Long id;

    @NotBlank(message = "Room type cannot be blank")
    private String type;

    @NotNull(message = "Room price cannot be blank" )
    private BigDecimal price;

    @NotNull(message = "Room photos cannot be blank" )
    private String[] photos;

    @NotNull(message = "Room aminities cannot be blank" )
    private String[] amanities;


    @NotNull(message = "Room totalcount cannot be blank" )
    private Integer totalCount;

    @NotNull(message = "Room capacity cannot be blank" )
    private Integer capacity;

}

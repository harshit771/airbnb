package com.practice.spring.airbnb.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class InventoryDto {

    
    private Long id;
    private LocalDate date;
    private Integer bookedCount;
    private Integer reservedCount;
    private Integer totalCount;
    private BigDecimal surgeFactor;
    private BigDecimal price; 
    private Boolean closed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
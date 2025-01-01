package com.practice.spring.airbnb.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.entities.Inventory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrappedPricingStrategy;
   
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrappedPricingStrategy.calculatePrice(inventory);
        
        LocalDate today=LocalDate.now();

        if(inventory.getDate().isBefore(today.plusDays(7))){
            price=price.multiply(BigDecimal.valueOf(1.15));
        }

        return price;
        
    }

}

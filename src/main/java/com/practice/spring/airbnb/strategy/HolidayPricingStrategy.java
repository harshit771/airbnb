package com.practice.spring.airbnb.strategy;

import java.math.BigDecimal;


import com.practice.spring.airbnb.entities.Inventory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class HolidayPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrappedPricingStrategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrappedPricingStrategy.calculatePrice(inventory);
        boolean isHoliday=true;
        if(isHoliday){
            price = price.multiply(BigDecimal.valueOf(1.25));
        }

        return price;
    }

}

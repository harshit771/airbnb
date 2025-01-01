package com.practice.spring.airbnb.strategy;

import java.math.BigDecimal;



import com.practice.spring.airbnb.entities.Inventory;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStrategy{

    private final PricingStrategy wrappedPricingStrategy;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price=wrappedPricingStrategy.calculatePrice(inventory);
        double occupacyRate=(double)inventory.getBookedCount()/inventory.getTotalCount();

        if(occupacyRate > 0.8){
            price=price.multiply(BigDecimal.valueOf(1.2));
        }

        return price;
    }

}

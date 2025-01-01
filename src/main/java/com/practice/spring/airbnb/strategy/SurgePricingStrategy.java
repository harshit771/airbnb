package com.practice.spring.airbnb.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.entities.Inventory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy{

    private final PricingStrategy wrappedPricingStrategy;

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
       return wrappedPricingStrategy.calculatePrice(inventory)
                                    .multiply(inventory.getSurgeFactor());
    }

    

}

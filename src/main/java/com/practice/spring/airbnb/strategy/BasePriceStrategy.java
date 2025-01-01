package com.practice.spring.airbnb.strategy;

import java.math.BigDecimal;

import com.practice.spring.airbnb.entities.Inventory;

public class BasePriceStrategy implements PricingStrategy{


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getPrice();
    }

}

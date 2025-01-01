package com.practice.spring.airbnb.strategy;

import java.math.BigDecimal;

import com.practice.spring.airbnb.entities.Inventory;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}

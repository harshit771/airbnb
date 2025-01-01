package com.practice.spring.airbnb.strategy;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.entities.Inventory;

@Service
public class PricingService {

    public BigDecimal calculateDynamicPricing(Inventory inventory){
        PricingStrategy pricingStrategy=new BasePriceStrategy();

        //applying additional strateging
        pricingStrategy=new SurgePricingStrategy(pricingStrategy);
        pricingStrategy=new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy=new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy=new  HolidayPricingStrategy(pricingStrategy);

        return pricingStrategy.calculatePrice(inventory);
    }

}

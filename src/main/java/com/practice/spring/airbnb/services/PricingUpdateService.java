package com.practice.spring.airbnb.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.HotelMinPrice;
import com.practice.spring.airbnb.entities.Inventory;
import com.practice.spring.airbnb.repositories.HotelMinPriceRepository;
import com.practice.spring.airbnb.repositories.HotelRepository;
import com.practice.spring.airbnb.repositories.InventoryRepository;
import com.practice.spring.airbnb.strategy.PricingService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PricingUpdateService {

    // schedular to update Inventory and HotelMinPrice price table every hours

    private final HotelRepository hotelRepository;
    private InventoryRepository inventoryRepository;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final PricingService pricingService;

    @Scheduled(cron = "0 0 * * * *")
    public void updatePrice() {
        int page = 0;
        int batchSize = 1000;

        while (true) {
            Page<Hotel> hotelPage = hotelRepository.findAll(PageRequest.of(page, batchSize));
            if (hotelPage.isEmpty()) {
                break;
            }
            hotelPage.getContent().forEach(this::updateHotelPrice);
            page++;
        }

    }

    private void updateHotelPrice(Hotel hotel) {
        LocalDate starDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusYears(1);

        List<Inventory> inventoryList = inventoryRepository.findByHotelAndDateBetween(hotel, starDate, endDate);
        updateInventoryPrices(inventoryList);
        updateHotelMinPrice(hotel, inventoryList, starDate, endDate);

    }

    private void updateHotelMinPrice(Hotel hotel, List<Inventory> inventoryList, LocalDate starDate,LocalDate endDate) {
        
        //compute minimum price per day for the hotel
        Map<LocalDate , BigDecimal > dailyMinPrice = inventoryList.stream()
                            .collect(Collectors.groupingBy(
                                Inventory :: getDate,
                                Collectors.mapping(Inventory :: getPrice , Collectors.minBy(Comparator.naturalOrder()))
                            ))
                            .entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry :: getKey, e -> e.getValue().orElse(BigDecimal.ZERO)));
                         
         
        //Prepare HotelPrice entities in bulk

        List<HotelMinPrice> hotelPrices =   new ArrayList<>();

        dailyMinPrice.forEach((date , price)->{
            HotelMinPrice hotelPrice = hotelMinPriceRepository.findByHotelAndDate(hotel,date)
                                .orElse(new HotelMinPrice(hotel,date));
            hotelPrice.setPrice(price);
            hotelPrices.add(hotelPrice);
        });
        hotelMinPriceRepository.saveAll(hotelPrices);          
    }

    private void updateInventoryPrices(List<Inventory> inventoryList) {
        inventoryList.forEach(inventory -> {
            BigDecimal dynamicPrice = pricingService.calculateDynamicPricing(inventory);
            inventory.setPrice(dynamicPrice);
        });
        inventoryRepository.saveAll(inventoryList);
    }
}

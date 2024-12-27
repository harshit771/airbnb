package com.practice.spring.airbnb.services.impl;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.entities.Inventory;
import com.practice.spring.airbnb.entities.Room;
import com.practice.spring.airbnb.repositories.InventoryRepository;
import com.practice.spring.airbnb.services.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    public void initializeRoomForAYear(Room room) {
       LocalDate today=LocalDate.now();
       LocalDate endDate=today.plusYears(1);
       for(;today.isBefore(endDate);today=today.plusDays(1)){
            Inventory inventory=Inventory.builder()
                                .hotel(room.getHotel())
                                .room(room)
                                .price(room.getPrice())
                                .bookedCount(0)
                                .city(room.getHotel().getCity())
                                .date(today)
                                .totalCount(room.getTotalCount())
                                .surgeFactor(BigDecimal.ONE)
                                .closed(false)
                                .build();

           inventoryRepository.save(inventory);                     
       }
    }

    @Override
    public void deleteAllInventories(Room room) {
        inventoryRepository.deleteByRoom(room);
    }

}

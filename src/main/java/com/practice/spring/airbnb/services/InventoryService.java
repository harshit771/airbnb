package com.practice.spring.airbnb.services;

import java.util.List;

import org.springframework.data.domain.Page;


import com.practice.spring.airbnb.dto.HotelPriceDto;
import com.practice.spring.airbnb.dto.HotelSearchRequest;
import com.practice.spring.airbnb.dto.InventoryDto;
import com.practice.spring.airbnb.entities.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);
}

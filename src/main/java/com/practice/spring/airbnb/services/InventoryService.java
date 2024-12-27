package com.practice.spring.airbnb.services;

import org.springframework.data.domain.Page;

import com.practice.spring.airbnb.dto.HotelDto;
import com.practice.spring.airbnb.dto.HotelSearchRequest;
import com.practice.spring.airbnb.entities.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}

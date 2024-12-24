package com.practice.spring.airbnb.services;

import com.practice.spring.airbnb.entities.Room;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteFutureInventories(Room room);
}

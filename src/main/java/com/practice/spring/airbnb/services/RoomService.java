package com.practice.spring.airbnb.services;

import java.util.List;

import com.practice.spring.airbnb.dto.RoomDto;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);

}

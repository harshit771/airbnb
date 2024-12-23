package com.practice.spring.airbnb.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.dto.RoomDto;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.Room;
import com.practice.spring.airbnb.exception.ResourceNotFoundException;
import com.practice.spring.airbnb.repositories.HotelRepository;
import com.practice.spring.airbnb.repositories.RoomRepository;
import com.practice.spring.airbnb.services.InventoryService;
import com.practice.spring.airbnb.services.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService{

     private final ModelMapper modelMapper;
     private final RoomRepository roomRepository;
     private final HotelRepository hotelRepository;
     private final InventoryService inventoryService;

    @Override
    public RoomDto createNewRoom(Long hotelId,RoomDto roomDto) {
       Room room= modelMapper.map(roomDto, Room.class);
        Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(
            ()-> new ResourceNotFoundException("Hotel not found with id "+hotelId));

        room.setHotel(hotel);
        room=roomRepository.save(room);

        if(hotel.isActive()){
            inventoryService.initializeRoomForAYear(room);
        }
        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        Hotel hotel=hotelRepository.findById(hotelId).orElseThrow(
            ()-> new ResourceNotFoundException("Hotel not found with id "+hotelId));


        return hotel.getRooms()
        .stream().map((elements) -> 
        modelMapper.map(elements, RoomDto.class)).
        collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        Room room=roomRepository.findById(roomId)
        .orElseThrow(
            ()-> new ResourceNotFoundException("Room not found with id "+roomId));
        
            return modelMapper.map(room, RoomDto.class);
            
    }

    @Override
    public void deleteRoomById(Long roomId) {
        Room room=roomRepository.findById(roomId)
        .orElseThrow(
            ()-> new ResourceNotFoundException("Room not found with id "+roomId));
        
        inventoryService.deleteFutureInventories(room);
        roomRepository.delete(room);

    }

}

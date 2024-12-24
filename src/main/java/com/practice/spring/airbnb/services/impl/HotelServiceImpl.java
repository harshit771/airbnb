package com.practice.spring.airbnb.services.impl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.practice.spring.airbnb.dto.HotelDto;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.Room;
import com.practice.spring.airbnb.repositories.HotelRepository;
import com.practice.spring.airbnb.services.HotelService;
import com.practice.spring.airbnb.services.InventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;


    @Override
    public HotelDto cretaeNewHotel(HotelDto hotelDto) {
       Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
       hotel.setActive(false);
       hotel=hotelRepository.save(hotel);
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                      .orElseThrow(() -> new ResourceAccessException("Hotel not found with id "+id));
        
                      
        return modelMapper.map(hotel,HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        Hotel hotel = hotelRepository.findById(id)
                      .orElseThrow(() -> new ResourceAccessException("Hotel not found with id "+id));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(id);
        Hotel updateHotel = hotelRepository.save(hotel);
        return modelMapper.map(updateHotel, HotelDto.class);

    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                      .orElseThrow(() -> new ResourceAccessException("Hotel not found with id "+id));

        for(Room room : hotel.getRooms()){
            inventoryService.deleteFutureInventories(room);
        }
        hotelRepository.delete(hotel);
        
    }

    @Override
    @Transactional
    public void activateHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id)
                      .orElseThrow(() -> new ResourceAccessException("Hotel not found with id "+id));
        hotel.setActive(true);

        for(Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }
    

}

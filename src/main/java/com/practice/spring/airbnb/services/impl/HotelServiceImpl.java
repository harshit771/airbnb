package com.practice.spring.airbnb.services.impl;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.practice.spring.airbnb.dto.HotelDto;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.repositories.HotelRepository;
import com.practice.spring.airbnb.services.HotelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService{

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;


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

    

}

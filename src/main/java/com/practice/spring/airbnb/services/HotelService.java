package com.practice.spring.airbnb.services;

import com.practice.spring.airbnb.dto.HotelDto;

public interface HotelService {

    HotelDto cretaeNewHotel(HotelDto hotel);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id,HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long id);


}

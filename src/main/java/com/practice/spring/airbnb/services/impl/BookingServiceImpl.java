package com.practice.spring.airbnb.services.impl;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.practice.spring.airbnb.dto.BookingDto;
import com.practice.spring.airbnb.dto.BookingRequest;
import com.practice.spring.airbnb.entities.Booking;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.Inventory;
import com.practice.spring.airbnb.entities.Room;
import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.entities.enums.BookingStatus;
import com.practice.spring.airbnb.repositories.BookingRepository;
import com.practice.spring.airbnb.repositories.HotelRepository;
import com.practice.spring.airbnb.repositories.InventoryRepository;
import com.practice.spring.airbnb.repositories.RoomRepository;
import com.practice.spring.airbnb.services.BookingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public BookingDto initialseBooking(BookingRequest bookingRequest) {
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                            .orElseThrow(() -> 
                            new ResourceAccessException("Hotel not found with id {}"+bookingRequest.getHotelId()));
                      
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                            .orElseThrow(() -> 
                            new ResourceAccessException("Hotel not found with id {}"+bookingRequest.getRoomId()));
                       
        List<Inventory> inventories= inventoryRepository.findAndLockInventory(bookingRequest.getRoomId(),
                                    bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate(),bookingRequest.getRoomsCount());                  
        
       
        long daysCount=ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(),bookingRequest.getCheckOutDate())+1;
        
        if(inventories.size() != daysCount){
            throw new IllegalStateException("Rooms are not available anymore");
        }

        for (Inventory inventory : inventories) {
            inventory.setReversedCount(inventory.getReversedCount()+bookingRequest.getRoomsCount());

        }
        inventoryRepository.saveAll(inventories);

        User user=new User();
        user.setId(1L);

        Booking booking = Booking.builder()
                            .bookingStatus(BookingStatus.RESERVED)
                            .hotel(hotel)
                            .room(room)
                            .checkInDate(bookingRequest.getCheckInDate())
                            .checkOutDate(bookingRequest.getCheckOutDate())
                            .user(user)
                            .roomsCount(bookingRequest.getRoomsCount())
                            .amount(BigDecimal.TEN)
                            .build();
       
        booking = bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);

    }

}

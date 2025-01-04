package com.practice.spring.airbnb.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.practice.spring.airbnb.dto.BookingDto;
import com.practice.spring.airbnb.dto.BookingRequest;
import com.practice.spring.airbnb.dto.GuestDto;
import com.practice.spring.airbnb.entities.Booking;
import com.practice.spring.airbnb.entities.Guest;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.Inventory;
import com.practice.spring.airbnb.entities.Room;
import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.entities.enums.BookingStatus;
import com.practice.spring.airbnb.exception.ResourceNotFoundException;
import com.practice.spring.airbnb.exception.UnAuthorizeException;
import com.practice.spring.airbnb.repositories.BookingRepository;
import com.practice.spring.airbnb.repositories.GuestRepositoy;
import com.practice.spring.airbnb.repositories.HotelRepository;
import com.practice.spring.airbnb.repositories.InventoryRepository;
import com.practice.spring.airbnb.repositories.RoomRepository;
import com.practice.spring.airbnb.services.BookingService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService{

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;
    private final GuestRepositoy guestRepositoy;

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

        Booking booking = Booking.builder()
                            .bookingStatus(BookingStatus.RESERVED)
                            .hotel(hotel)
                            .room(room)
                            .checkInDate(bookingRequest.getCheckInDate())
                            .checkOutDate(bookingRequest.getCheckOutDate())
                            .user(getCurrentUser())
                            .roomsCount(bookingRequest.getRoomsCount())
                            .amount(BigDecimal.TEN)
                            .build();
       
        booking = bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);

    }

    @Override
    public BookingDto addGuest(Long bookingId, List<GuestDto> guestList) {
        log.info("Adding guest ");

        Booking booking=bookingRepository.findById(bookingId)
                                .orElseThrow(() -> 
                                new ResourceNotFoundException("Booking not found with id "+bookingId));
        
        User user=getCurrentUser();
        if(!user.equals(booking.getUser())){
            throw new UnAuthorizeException("Booking does not belong to this user with id "+user.getId());
        }                       
        if(isBookingExpired(booking)){
            throw new IllegalStateException("Booking has already expired");
        }

        if(booking.getBookingStatus() != BookingStatus.RESERVED){
            throw new IllegalStateException("Booking is not under revesed statud , can not add guest");
        }

        for (GuestDto guestDto : guestList) {
            Guest guest=modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest=guestRepositoy.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking=bookingRepository.save(booking);
        return modelMapper.map(booking,BookingDto.class);

    }

    public boolean isBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser(){
        User user=(User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

}

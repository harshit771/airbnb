package com.practice.spring.airbnb.services;

import java.time.LocalDate;
import java.util.List;

import com.practice.spring.airbnb.dto.BookingDto;
import com.practice.spring.airbnb.dto.BookingRequest;
import com.practice.spring.airbnb.dto.GuestDto;
import com.practice.spring.airbnb.dto.HotelReportDto;
import com.stripe.model.Event;

public interface BookingService {

    BookingDto initialseBooking(BookingRequest bookingRequest);

    BookingDto addGuest(Long bookingId, List<GuestDto> guestList);

    String initiatePayment(Long bookingId);

    void capturePayment(Event event);

    void cancelPayment(Long bookingId);

    String getBookingStatus(Long bookingId);

    List<BookingDto> getAllBookingsByHotels(Long hotelId);

    HotelReportDto getHotelReport(Long hotelId, LocalDate starDate, LocalDate endDate);

    List<BookingDto> getMyBooking();

}

package com.practice.spring.airbnb.services;

import java.util.List;

import com.practice.spring.airbnb.dto.BookingDto;
import com.practice.spring.airbnb.dto.BookingRequest;
import com.practice.spring.airbnb.dto.GuestDto;
import com.stripe.model.Event;

public interface BookingService {

    BookingDto initialseBooking(BookingRequest bookingRequest);

    BookingDto addGuest(Long bookingId, List<GuestDto> guestList);

    String initiatePayment(Long bookingId);

    void capturePayment(Event event);

}

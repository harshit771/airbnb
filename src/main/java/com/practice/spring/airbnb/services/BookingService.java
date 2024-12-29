package com.practice.spring.airbnb.services;

import com.practice.spring.airbnb.dto.BookingDto;
import com.practice.spring.airbnb.dto.BookingRequest;

public interface BookingService {

    BookingDto initialseBooking(BookingRequest bookingRequest);

}

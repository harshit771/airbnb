package com.practice.spring.airbnb.services;

import com.practice.spring.airbnb.entities.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}

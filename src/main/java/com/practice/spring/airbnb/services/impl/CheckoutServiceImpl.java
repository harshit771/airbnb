package com.practice.spring.airbnb.services.impl;

import java.math.BigDecimal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.practice.spring.airbnb.entities.Booking;
import com.practice.spring.airbnb.entities.User;
import com.practice.spring.airbnb.repositories.BookingRepository;
import com.practice.spring.airbnb.services.CheckoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final BookingRepository bookingRepository;

    @Override
    public String getCheckoutSession(Booking booking, String successUrl, String failureUrl) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Customer customer = Customer.create(
                    CustomerCreateParams.builder()
                            .setName(user.getName())
                            .setEmail(user.getEmail())
                            .build());

            SessionCreateParams sessionParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                    .setCustomer(customer.getId())
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(failureUrl)
                    .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                    .setQuantity(1L)
                                    .setPriceData(
                                            SessionCreateParams.LineItem.PriceData.builder()
                                                    .setCurrency("INR")
                                                    .setUnitAmount(booking.getAmount().multiply(BigDecimal.valueOf(100))
                                                            .longValue())
                                                    .setProductData(
                                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                    .setName(booking.getHotel().getName() + " : "
                                                                            + booking.getRoom().getType())
                                                                    .setDescription("Booking Id " + booking.getId())
                                                                    .build()

                                                    )
                                                    .build())
                                    .build()

                    )
                    .build();

            Session session = Session.create(sessionParams);
            booking.setPaymentSessionId(session.getId());
            bookingRepository.save(booking);
            return session.getUrl();
        } catch (StripeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }

    }

}

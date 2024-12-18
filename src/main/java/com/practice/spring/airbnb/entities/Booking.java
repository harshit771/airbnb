package com.practice.spring.airbnb.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.practice.spring.airbnb.entities.enums.BookingStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="booking")
public class Booking {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hotel_id",nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id",nullable = false)
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer roomsCount;

    @Column(nullable = false)
    private LocalDate checkInDate;

    @Column(nullable = false)
    private LocalDate checkOutDate;

     @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name="booking_guest",
        joinColumns = @JoinColumn(name="booking_id"),
        inverseJoinColumns = @JoinColumn(name="guest_id")
        )

    private Set<Guest> guests;

}

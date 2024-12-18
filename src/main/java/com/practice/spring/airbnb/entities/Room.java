package com.practice.spring.airbnb.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="hotel_id",nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false,precision = 10,scale = 2)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amanities;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false)
    private Integer capacity;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    


}

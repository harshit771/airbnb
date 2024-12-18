package com.practice.spring.airbnb.entities;

import java.util.Set;

import com.practice.spring.airbnb.entities.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable =  false)
    private String name;

    @Column(nullable =  false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable =  false)
    private Integer age;

    @ManyToMany(mappedBy = "guests")
    private Set<Booking> bookings;

}

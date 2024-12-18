package com.practice.spring.airbnb.entities;

import java.util.Set;

import com.practice.spring.airbnb.entities.enums.Role;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true , nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;



}

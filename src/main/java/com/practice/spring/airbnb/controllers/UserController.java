package com.practice.spring.airbnb.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.spring.airbnb.dto.BookingDto;
import com.practice.spring.airbnb.dto.ProfileUpdateRequestDto;
import com.practice.spring.airbnb.dto.UserDto;
import com.practice.spring.airbnb.services.BookingService;
import com.practice.spring.airbnb.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BookingService bookingService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequestDto profileUpdateRequestDto){
        userService.updateProfile(profileUpdateRequestDto);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<BookingDto>> getMyBooking(){
        return ResponseEntity.ok(bookingService.getMyBooking());
    }

    @GetMapping("/myprofile")
    public ResponseEntity<UserDto> getMyProfile(){
        return ResponseEntity.ok(userService.getMyProfile());
    }

}

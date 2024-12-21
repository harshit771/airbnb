package com.practice.spring.airbnb.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practice.spring.airbnb.dto.HotelDto;
import com.practice.spring.airbnb.exception.ResourceNotFoundException;
import com.practice.spring.airbnb.services.HotelService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin/hotels")
@RequiredArgsConstructor
@Slf4j
public class HotelController {

    private final HotelService hotelService;

  
    @PostMapping
    public ResponseEntity<HotelDto>  createNewHotel(@RequestBody HotelDto hotelDto){
        HotelDto hotel=hotelService.cretaeNewHotel(hotelDto);

        return new ResponseEntity<>(hotel,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelDto> getHotelById(@PathVariable Long id){
        HotelDto hotel=hotelService.getHotelById(id);
        if(hotel == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(hotel,HttpStatus.OK);
        }
    }

}

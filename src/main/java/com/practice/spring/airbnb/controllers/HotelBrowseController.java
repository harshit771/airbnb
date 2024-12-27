package com.practice.spring.airbnb.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.spring.airbnb.dto.HotelDto;
import com.practice.spring.airbnb.dto.HotelSearchRequest;
import com.practice.spring.airbnb.services.InventoryService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelBrowseController {

    private final InventoryService inventoryService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotel(@RequestBody HotelSearchRequest hotelSearchRequest){
        Page<HotelDto> page=inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(page);
    }
    
}

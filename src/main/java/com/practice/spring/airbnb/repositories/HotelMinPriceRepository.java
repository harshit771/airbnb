package com.practice.spring.airbnb.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.practice.spring.airbnb.dto.HotelPriceDto;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.HotelMinPrice;

public interface HotelMinPriceRepository extends JpaRepository<HotelMinPrice,Long>{

    @Query("""
            Select distinct com.practice.spring.airbnb.dto.HotelPriceDto(i.hotel,AVG(i.price))
            from HotelMinPrice i where i.hotel.city = :city
            and i.date between :startDate and :endDate
            and i.hotel.active=true 
            group by i.hotel
            """)
    Page<HotelPriceDto> findHotelsWithAvailableInventory(
        @Param("city") String city,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable

    );

    Optional<HotelMinPrice> findByHotelAndDate(Hotel hotel, LocalDate date);
}

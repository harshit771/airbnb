package com.practice.spring.airbnb.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.Inventory;
import com.practice.spring.airbnb.entities.Room;

import jakarta.persistence.LockModeType;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long>{

    void deleteByRoom(Room room);

    @Query("""
            Select distinct i.hotel from Inventory i where i.city = :city
            and i.date between :startDate and :endDate
            and i.closed=false 
            and (i.totalCount - i.bookedCount - i.reversedCount) >= :roomsCount
            group by i.hotel,i.room having count(i.date) = :dateCount
            """)
    Page<Hotel> findHotelsWithAvailableInventory(
        @Param("city") String city,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("roomsCount")Integer roomsCount,
        @Param("dateCount")long dateCount,
        Pageable pageable

    );

    @Query("""
            Select i from Inventory i where i.room.id = :roomId 
            and i.date between :startDate and :endDate
            and i.closed=false 
            and (i.totalCount - i.bookedCount - i.reversedCount ) >= :roomsCount
            """)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Inventory> findAndLockInventory(
        @Param("roomId") Long roomId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("roomsCount")Integer roomsCount
    );

    List<Inventory> findByHotelAndDateBetween(Hotel hotel, LocalDate starDate, LocalDate endDate);

}

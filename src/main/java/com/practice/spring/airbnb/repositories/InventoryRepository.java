package com.practice.spring.airbnb.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.practice.spring.airbnb.entities.Hotel;
import com.practice.spring.airbnb.entities.Inventory;
import com.practice.spring.airbnb.entities.Room;

import jakarta.persistence.LockModeType;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

        void deleteByRoom(Room room);

        @Query("""
                        Select distinct i.hotel from Inventory i where i.city = :city
                        and i.date between :startDate and :endDate
                        and i.closed=false
                        and (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
                        group by i.hotel,i.room having count(i.date) = :dateCount
                        """)
        Page<Hotel> findHotelsWithAvailableInventory(
                        @Param("city") String city,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("roomsCount") Integer roomsCount,
                        @Param("dateCount") long dateCount,
                        Pageable pageable

        );

        @Query("""
                        Select i from Inventory i where i.room.id = :roomId
                        and i.date between :startDate and :endDate
                        and i.closed=false
                        and (i.totalCount - i.bookedCount - i.reservedCount ) >= :roomsCount
                        """)
        @Lock(LockModeType.PESSIMISTIC_WRITE)
        List<Inventory> findAndLockInventory(
                        @Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("roomsCount") Integer roomsCount);

        @Query("""
                            SELECT i
                            FROM Inventory i
                            WHERE i.room.id = :roomId
                              AND i.date BETWEEN :startDate AND :endDate
                              AND (i.totalCount - i.bookedCount) >= :numberOfRooms
                              AND i.closed = false
                        """)
        @Lock(LockModeType.PESSIMISTIC_WRITE)
        List<Inventory> findAndLockReservedInventory(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("numberOfRooms") int numberOfRooms);

        @Modifying
        @Query("""
                            UPDATE Inventory i
                            SET i.reservedCount = i.reservedCount + :numberOfRooms
                            WHERE i.room.id = :roomId
                              AND i.date BETWEEN :startDate AND :endDate
                              AND (i.totalCount - i.bookedCount - i.reservedCount) >= :numberOfRooms
                              AND i.closed = false
                        """)
        void initBooking(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("numberOfRooms") int numberOfRooms);

        @Modifying
        @Query("""
                            UPDATE Inventory i
                            SET i.reservedCount = i.reservedCount - :numberOfRooms,
                                i.bookedCount = i.bookedCount + :numberOfRooms
                            WHERE i.room.id = :roomId
                              AND i.date BETWEEN :startDate AND :endDate
                              AND (i.totalCount - i.bookedCount) >= :numberOfRooms
                              AND i.reservedCount >= :numberOfRooms
                              AND i.closed = false
                        """)
        void confirmBooking(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("numberOfRooms") int numberOfRooms);

        @Modifying
        @Query("""
                            UPDATE Inventory i
                            SET i.bookedCount = i.bookedCount - :numberOfRooms
                            WHERE i.room.id = :roomId
                              AND i.date BETWEEN :startDate AND :endDate
                              AND (i.totalCount - i.bookedCount) >= :numberOfRooms
                              AND i.closed = false
                        """)
        void cancelBooking(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("numberOfRooms") int numberOfRooms);

        List<Inventory> findByHotelAndDateBetween(Hotel hotel, LocalDate starDate, LocalDate endDate);

        List<Inventory> findByRoomOrderByDate(Room room);

        @Query("""
                            Select i Inventory i
                            WHERE i.room.id = :roomId
                            AND i.date BETWEEN :startDate AND :endDate
                        """)
        @Lock(LockModeType.PESSIMISTIC_WRITE)
        void getInventoryAndLockBeforeUpdate(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("closed") boolean closed,
                        @Param("surgeFactor") BigDecimal surgeFactor);

        @Modifying
        @Query("""
                            UPDATE Inventory i
                            SET i.surgeFactor = :surgeFactor and i.closed = :closed;
                            WHERE i.room.id = :roomId
                            AND i.date BETWEEN :startDate AND :endDate
                        """)
        void updateInventory(@Param("roomId") Long roomId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate,
                        @Param("closed") boolean closed,
                        @Param("surgeFactor") BigDecimal surgeFactor);

}

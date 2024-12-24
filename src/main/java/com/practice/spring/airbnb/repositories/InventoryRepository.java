package com.practice.spring.airbnb.repositories;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.spring.airbnb.entities.Inventory;
import com.practice.spring.airbnb.entities.Room;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long>{

    void deleteByDateAfterAndRoom(LocalDate date,Room room);

}

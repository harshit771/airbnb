package com.practice.spring.airbnb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.spring.airbnb.entities.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long>{

}

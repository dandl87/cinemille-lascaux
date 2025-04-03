package com.delorenzo.Cinema.repository;

import com.delorenzo.Cinema.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long>{
    Optional<Room> findByName(String name);
    List<Room> findAllByImax(Boolean value);
}

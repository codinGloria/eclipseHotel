package com.liviabraz.eclipseHotel.repository;

import com.liviabraz.eclipseHotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}

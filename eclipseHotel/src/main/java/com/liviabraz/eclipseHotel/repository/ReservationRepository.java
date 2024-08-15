package com.liviabraz.eclipseHotel.repository;

import com.liviabraz.eclipseHotel.entity.Reservation;
import com.liviabraz.eclipseHotel.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByCheckinBetween(LocalDateTime start, LocalDateTime end);
    List<Reservation> findByStatus(ReservationStatus status);
}

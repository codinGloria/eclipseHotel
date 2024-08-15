package com.liviabraz.eclipseHotel.controller;

import com.liviabraz.eclipseHotel.dto.CustomerDTO;
import com.liviabraz.eclipseHotel.dto.ReservationDTO;
import com.liviabraz.eclipseHotel.dto.RoomDTO;
import com.liviabraz.eclipseHotel.entity.Customer;
import com.liviabraz.eclipseHotel.entity.Reservation;
import com.liviabraz.eclipseHotel.entity.Room;
import com.liviabraz.eclipseHotel.enums.ReservationStatus;
import com.liviabraz.eclipseHotel.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/open")
    public ResponseEntity<ReservationDTO> openReservation(@RequestBody ReservationDTO reservationDTO) {
        Reservation createdReservation = reservationService.openReservation(reservationDTO);
        return ResponseEntity.ok(toDTO(createdReservation));
    }

    @PutMapping("/close/{id}")
    public ResponseEntity<ReservationDTO> closeReservation(@PathVariable Long id) {
        Reservation closedReservation = reservationService.closeReservation(id);
        return ResponseEntity.ok(toDTO(closedReservation));
    }

    @GetMapping("/by-dates")
    public ResponseEntity<List<ReservationDTO>> getReservationsByDateRange(
            @RequestParam("start") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate start,
            @RequestParam("end") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate end) {

        // Convertendo LocalDate para LocalDateTime com horário inicial e final do dia
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(23, 59, 59);

        List<Reservation> reservations = reservationService.getReservationsByDateRange(startDateTime, endDateTime);
        List<ReservationDTO> reservationDTOs = reservations.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }

    @GetMapping("/occupied")
    public ResponseEntity<List<ReservationDTO>> getOccupiedRooms() {
        List<Reservation> occupiedRooms = reservationService.getOccupiedRooms();
        List<ReservationDTO> occupiedRoomDTOs = occupiedRooms.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(occupiedRoomDTOs);
    }

    private ReservationDTO toDTO(Reservation reservation) {
        ReservationDTO dto = new ReservationDTO();
        dto.setId(reservation.getId());
        dto.setCheckin(reservation.getCheckin());
        dto.setCheckout(reservation.getCheckout());
        dto.setStatus(reservation.getStatus());
        dto.setCustomerId(reservation.getCustomer().getId());
        dto.setRoomId(reservation.getRoom().getId());
        return dto;
    }
}

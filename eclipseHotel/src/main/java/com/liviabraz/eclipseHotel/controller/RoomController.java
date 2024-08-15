package com.liviabraz.eclipseHotel.controller;

import com.liviabraz.eclipseHotel.dto.RoomDTO;
import com.liviabraz.eclipseHotel.entity.Reservation;
import com.liviabraz.eclipseHotel.entity.Room;
import com.liviabraz.eclipseHotel.exception.ResourceNotFoundException;
import com.liviabraz.eclipseHotel.repository.RoomRepository;
import com.liviabraz.eclipseHotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        Room room = new Room();
        room.setRoom_number(roomDTO.getRoom_number());
        room.setType(roomDTO.getType());
        room.setPrice(roomDTO.getPrice());
        Room createdRoom = roomService.createRoom(room);
        return ResponseEntity.ok(toDTO(createdRoom));
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDTO> roomDTOs = rooms.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roomDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoomById(@PathVariable Long id) {
        Room room = roomService.getRoomById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quarto não encontrado com o id: " + id));
        return ResponseEntity.ok(toDTO(room));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO roomDTO) {
        Room room = new Room();
        room.setId(id);
        room.setRoom_number(roomDTO.getRoom_number());
        room.setType(roomDTO.getType());
        room.setPrice(roomDTO.getPrice());
        Room updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(toDTO(updatedRoom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    private RoomDTO toDTO(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setRoom_number(room.getRoom_number());
        dto.setType(room.getType());
        dto.setPrice(room.getPrice());
        dto.setReservations(room.getReservations().stream()
                .map(Reservation::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}
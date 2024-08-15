package com.liviabraz.eclipseHotel.service;

import com.liviabraz.eclipseHotel.entity.Room;
import com.liviabraz.eclipseHotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository repository;

    public Room createRoom(Room room) {
        logger.info("Criando quarto: {}", room);
        return repository.save(room);
    }

    public List<Room> getAllRooms() {
        logger.info("🔍 Buscando todos os quartos...");
        return repository.findAll();
    }

    public Optional<Room> getRoomById(Long id) {
        logger.info("🔍 Buscando quarto com ID: {}", id);
        return repository.findById(id);
    }

    public Room updateRoom(Long id, Room roomInf) {
        logger.info("Atualizando quarto com ID: {}", id);
        Room toUpdate = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Quarto não encontrado"));
        toUpdate.setRoom_number(roomInf.getRoom_number());
        toUpdate.setType(roomInf.getType());
        toUpdate.setPrice(roomInf.getPrice());

        Room updatedRoom = repository.save(toUpdate);

        logger.info("Quarto atualizado: {}", updatedRoom);

        return updatedRoom;
    }

    public void deleteRoom(Long id) {
        logger.info("Deletando quarto com ID: {}", id);
        repository.deleteById(id);
    }
}

package com.liviabraz.eclipseHotel.service;

import com.liviabraz.eclipseHotel.dto.ReservationDTO;
import com.liviabraz.eclipseHotel.entity.Customer;
import com.liviabraz.eclipseHotel.entity.Reservation;
import com.liviabraz.eclipseHotel.entity.Room;
import com.liviabraz.eclipseHotel.enums.ReservationStatus;
import com.liviabraz.eclipseHotel.repository.CustomerRepository;
import com.liviabraz.eclipseHotel.repository.ReservationRepository;
import com.liviabraz.eclipseHotel.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public Reservation openReservation(ReservationDTO reservationDTO) {
        logger.info("Abrindo reserva para o cliente com ID: {}", reservationDTO.getCustomerId());

        Customer customer = customerRepository.findById(reservationDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        Room room = roomRepository.findById(reservationDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));

        Reservation reservation = new Reservation();
        reservation.setCheckin(reservationDTO.getCheckin());
        reservation.setCheckout(reservationDTO.getCheckout());
        reservation.setStatus(ReservationStatus.SCHEDULED);
        reservation.setCustomer(customer);
        reservation.setRoom(room);

        Reservation createdReservation = reservationRepository.save(reservation);
        logger.info("Reserva criada: {}", createdReservation);

        customer.getReservations().add(createdReservation);
        room.getReservations().add(createdReservation);
        customerRepository.save(customer);
        roomRepository.save(room);

        logger.info("Reserva armazenada com sucesso, ID: {}", createdReservation.getId());
        return createdReservation;
    }

    @Transactional
    public Reservation closeReservation(Long id) {
        logger.info("Fechando reserva número {}", id);

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada."));

        reservation.setStatus(ReservationStatus.FINISHED);

        Reservation closedReservation = reservationRepository.save(reservation);

        logger.info("Reserva número {} fechada com sucesso.", id);
        return closedReservation;
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByDateRange(LocalDateTime start, LocalDateTime end){
        logger.info("🔍 Buscando reservas de {} à {}...", start, end);
        return reservationRepository.findByCheckinBetween(start, end);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getOccupiedRooms(){
        logger.info("🔍 Buscando quartos ocupados...");
        return reservationRepository.findByStatus(ReservationStatus.IN_USE);
    }
}

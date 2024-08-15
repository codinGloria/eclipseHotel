package com.liviabraz.eclipseHotel.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liviabraz.eclipseHotel.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDTO {

    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime checkin;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime checkout;

    private ReservationStatus status;
    private Long customerId;
    private Long roomId;
}

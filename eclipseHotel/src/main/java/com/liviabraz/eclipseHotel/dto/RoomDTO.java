package com.liviabraz.eclipseHotel.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoomDTO {
    private Long id;
    private String room_number;
    private String type;
    private Double price;
    private List<Long> reservations;

}


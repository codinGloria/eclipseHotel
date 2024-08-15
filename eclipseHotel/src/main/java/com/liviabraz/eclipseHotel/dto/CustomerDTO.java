package com.liviabraz.eclipseHotel.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createAt;
    private List<Long> reservations;

}

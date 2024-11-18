package org.example.backend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationDTO {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long numberOfGuests;
    private Double totalPrice;
    private String status;
    private Long clientId;
    private Long roomId;
}

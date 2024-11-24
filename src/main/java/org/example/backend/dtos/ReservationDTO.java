package org.example.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ReservationDTO {

    @Schema(description = "The unique identifier of the reservation", example = "1")
    private Long id;

    @Schema(description = "The date when the client checks in", example = "2024-12-01")
    private LocalDate checkInDate;

    @Schema(description = "The date when the client checks out", example = "2024-12-07")
    private LocalDate checkOutDate;

    @Schema(description = "The total number of guests for the reservation", example = "2")
    private Long numberOfGuests;

    @Schema(description = "The total price for the reservation", example = "500.00")
    private Double totalPrice;

    @Schema(description = "The status of the reservation (e.g., 'available', 'canceled')", example = "available")
    private String status;

    @Schema(description = "The ID of the client who made the reservation", example = "1")
    private Long clientId;

    @Schema(description = "The ID of the room reserved", example = "101")
    private Long roomId;
}

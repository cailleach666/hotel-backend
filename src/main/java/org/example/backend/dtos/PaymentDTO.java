package org.example.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PaymentDTO {

    @Schema(description = "Unique identifier of the payment", example = "1")
    private long id;

    @Schema(description = "Credit card number used for payment",
            example = "4111111111111111",
            format = "string")
    private String cardNumber;

    @Schema(description = "Date of the payment",
           example = "2024-12-01",
           format = "date")
    private LocalDate paymentDate;

    @Schema(description = "Amount paid",
            example = "100.50")
    private double amount;

    @Schema(description = "Status of the payment",
            example = "COMPLETED",
            allowableValues = {"PENDING", "COMPLETED", "FAILED"})
    private String status;

    @Schema(description = "ID of the client associated with the payment",
            example = "1")
    private Long clientId;

    @Schema(description = "ID of the reservation associated with the payment",
            example = "1")
    private Long reservationId;
}

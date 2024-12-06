package org.example.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Card number cannot be null")
    @Size(min = 16, max = 16, message = "Card number must be 16 digits")
    @Pattern(regexp = "\\d{16}", message = "Card number must contain only digits")
    private String cardNumber;

    @Schema(description = "Date of the payment",
           example = "2024-12-01",
           format = "date")
    @NotNull(message = "Payment date cannot be null")
    @PastOrPresent(message = "Payment date cannot be in the future")
    private LocalDate paymentDate;

    @Schema(description = "Amount paid",
            example = "100.50")
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be greater than zero")
    private double amount;

    @Schema(description = "Status of the payment",
            example = "COMPLETED",
            allowableValues = {"PENDING", "COMPLETED", "FAILED"})
    @NotNull(message = "Status cannot be null")
    private String status;

    @Schema(description = "ID of the client associated with the payment",
            example = "1")
    @NotNull(message = "Client ID cannot be null")
    @Positive(message = "Client ID must be a positive number")
    private Long clientId;

    @Schema(description = "ID of the reservation associated with the payment",
            example = "1")
    @NotNull(message = "Reservation ID cannot be null")
    @Positive(message = "Reservation ID must be a positive number")
    private Long reservationId;
}

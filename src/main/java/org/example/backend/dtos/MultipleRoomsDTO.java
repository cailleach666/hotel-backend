package org.example.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.enums.RoomType;

@Data
@NoArgsConstructor
public class MultipleRoomsDTO {

    @Schema(description = "The room number", example = "101")
    @NotBlank(message = "Room number cannot be blank")
    @Pattern(regexp = "\\d{1,4}", message = "Room number must be between 1 to 4 digits")
    private String startRoomNumber;

    @Schema(description = "The total number of rooms to create", example = "2")
    @NotNull(message = "Number of rooms cannot be null")
    @Min(value = 1, message = "There must be at least one room")
    private int numberOfRooms;

    @Schema(description = "The price per night for the room", example = "150.00")
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 6, fraction = 2, message = "Price must be a valid monetary value with up to 2 decimal places")
    private double price;

    @Schema(description = "The type of room (e.g., SINGLE, DOUBLE)", example = "SINGLE")
    @NotNull(message = "Room type cannot be null")
    private RoomType type;
}

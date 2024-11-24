package org.example.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.enums.RoomType;

@Data
@NoArgsConstructor
public class RoomDTO {

    @Schema(description = "The unique identifier of the room", example = "1")
    private Long id;

    @Schema(description = "The room number", example = "101")
    private String roomNumber;

    @Schema(description = "The price per night for the room", example = "150.00")
    private Double price;

    @Schema(description = "Indicates whether the room is available", example = "true")
    private boolean available;

    @Schema(description = "The type of room (e.g., SINGLE, DOUBLE)", example = "SINGLE")
    private RoomType type;

    @Schema(description = "A brief description of the room", example = "A comfortable single room with modern amenities.")
    private String description;
}

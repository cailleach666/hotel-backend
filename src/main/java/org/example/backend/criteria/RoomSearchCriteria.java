package org.example.backend.criteria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.example.backend.enums.RoomType;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class RoomSearchCriteria {
    @Schema(description = "The type of room to search for (e.g., SINGLE, DOUBLE, SUITE)", example = "SINGLE")
    private RoomType type;

    @Schema(description = "The minimum price of the room", example = "50.00")
    private Double minPrice;

    @Schema(description = "The maximum price of the room", example = "500.00")
    private Double maxPrice;

    @Schema(description = "Indicates whether the room should be available", example = "true")
    private Boolean available;

    @Schema(description = "Check-in date for room availability", example = "2024-12-01")
    private LocalDate checkInDate;

    @Schema(description = "Check-out date for room availability", example = "2024-12-07")
    private LocalDate checkOutDate;

    private final Optional<String> sortDirection; // sd = Sort Direction
    private final Optional<Integer> page; // p = page
}

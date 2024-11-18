package org.example.backend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.backend.enums.RoomType;

@Data
@NoArgsConstructor
public class RoomDTO {

    private Long id;
    private String roomNumber;
    private Double price;
    private boolean available;
    private RoomType type;
    private String description;

}

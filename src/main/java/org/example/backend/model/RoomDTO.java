package org.example.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomDTO {

    private Long id;
    private String roomnumber;
    private Double price;
    private boolean available;
    private RoomType type;

}

package org.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.enums.RoomType;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomnumber;
    private Double price;
    private boolean available;
    @Enumerated(EnumType.STRING)
    private RoomType type;
}

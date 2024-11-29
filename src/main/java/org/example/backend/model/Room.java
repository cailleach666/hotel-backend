package org.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.backend.enums.RoomType;
import java.util.List;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private boolean available;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "room_amenities",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<Amenity> amenities;
}

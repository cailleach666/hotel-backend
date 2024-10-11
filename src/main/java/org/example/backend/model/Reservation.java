package org.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate checkindate;
    private LocalDate checkoutdate;
    private String status;

//    @ManyToOne
//    @JoinColumn(name = "client_id")
//    private Client client;
//
//    @ManyToOne
//    @JoinColumn(name = "room_id")
//    private Room room;
}

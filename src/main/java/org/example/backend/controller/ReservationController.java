package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dtos.ReservationDTO;
import org.example.backend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        log.info("Received request to create a reservation: {}", reservationDTO);
        ReservationDTO createdReservation = reservationService.createReservation(reservationDTO);
        log.info("Reservation created successfully: {}", createdReservation);
        return ResponseEntity.ok(createdReservation);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        log.info("Fetching all reservations");
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        log.info("Found {} reservations", reservations.size());
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByClientId(@PathVariable Long clientId) {
        log.info("Fetching reservations for client ID: {}", clientId);
        List<ReservationDTO> reservations = reservationService.getReservationsByClientId(clientId);
        log.info("Found {} reservations for client ID: {}", reservations.size(), clientId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {
        log.info("Fetching reservation with ID: {}", id);
        ReservationDTO reservation = reservationService.getReservation(id);
        if (reservation != null) {
            log.info("Reservation found: {}", reservation);
        } else {
            log.warn("Reservation with ID {} not found", id);
        }
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable Long id, @RequestBody ReservationDTO updatedReservationDTO) {
        log.info("Updating reservation with ID: {}. New data: {}", id, updatedReservationDTO);
        ReservationDTO updatedReservation = reservationService.updateReservation(id, updatedReservationDTO);
        log.info("Reservation updated successfully: {}", updatedReservation);
        return ResponseEntity.ok(updatedReservation);    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        log.info("Deleting reservation with ID: {}", id);
        reservationService.deleteReservation(id);
        log.info("Reservation with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}

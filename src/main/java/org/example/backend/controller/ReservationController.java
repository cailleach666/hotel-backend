package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.ReservationDTO;
import org.example.backend.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservations")
@Tag(name = "Reservation", description = "Operations related to reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @Operation(summary = "Create a new reservation", description = "Create a new reservation and return the reservation details")
    @ApiResponse(responseCode = "200", description = "Reservation created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid reservation data")
    public ResponseEntity<ReservationDTO> createReservation(
            @RequestBody @Parameter(description = "Reservation details") ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO));
    }

    @GetMapping
    @Operation(summary = "Get list of reservations", description = "Retrieve a list of all reservations")
    @ApiResponse(responseCode = "200", description = "List of reservations")
    public ResponseEntity<List<ReservationDTO>> getReservations() {
        List<ReservationDTO> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/client/{clientId}")
    @Operation(summary = "Get reservations by client ID", description = "Retrieve a list of reservations for a specific client")
    @ApiResponse(responseCode = "200", description = "List of reservations for the client")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<List<ReservationDTO>> getReservationsByClientId(
            @PathVariable @Parameter(description = "Client ID") Long clientId) {
        List<ReservationDTO> reservations = reservationService.getReservationsByClientId(clientId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservation by ID", description = "Retrieve a reservation by its unique ID")
    @ApiResponse(responseCode = "200", description = "Reservation found")
    @ApiResponse(responseCode = "404", description = "Reservation not found")
    public ResponseEntity<ReservationDTO> getReservation(
            @PathVariable @Parameter(description = "Reservation ID") Long id) {
        return ResponseEntity.ok(reservationService.getReservation(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update reservation details", description = "Update the details of a specific reservation")
    @ApiResponse(responseCode = "200", description = "Reservation updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid reservation data")
    @ApiResponse(responseCode = "404", description = "Reservation not found")
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable @Parameter(description = "Reservation ID") Long id,
            @RequestBody @Parameter(description = "Updated reservation details") ReservationDTO updatedReservationDTO) {
        return ResponseEntity.ok(reservationService.updateReservation(id, updatedReservationDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reservation", description = "Delete a reservation by its unique ID")
    @ApiResponse(responseCode = "204", description = "Reservation deleted successfully")
    @ApiResponse(responseCode = "404", description = "Reservation not found")
    public ResponseEntity<Void> deleteReservation(@PathVariable @Parameter(description = "Reservation ID") Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}

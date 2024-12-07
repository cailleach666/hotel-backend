package org.example.backend.controller.room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.criteria.RoomSearchCriteria;
import org.example.backend.dtos.AmenityDTO;
import org.example.backend.dtos.MultipleRoomsDTO;
import org.example.backend.service.reservation.ReservationService;
import org.example.backend.service.room.RoomAmenityService;
import org.springframework.data.domain.Pageable;

import org.example.backend.dtos.RoomDTO;
import org.example.backend.service.room.RoomService;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
@Tag(name = "Room", description = "Operations related to rooms")
public class RoomController {

    private final RoomService roomService;
    private final ReservationService reservationService;
    private final RoomAmenityService roomAmenityService;

    @PostMapping("/private")
    @Operation(summary = "Create a new room", description = "Create a new room and return the room details")
    @ApiResponse(responseCode = "200", description = "Room created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid room data")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Valid @Parameter(description = "Room details") RoomDTO roomDTO) {
        log.info("Received request to create a room: {}", roomDTO);
        RoomDTO createdRoom = roomService.createRoom(roomDTO);
        log.info("Room created successfully: {}", createdRoom);
        return ResponseEntity.ok(createdRoom);    }

    @PostMapping("/private/create-multiple")
    @Operation(summary = "Create multiple rooms", description = "Create a series of rooms starting from a specific room number")
    @ApiResponse(responseCode = "200", description = "Rooms created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid room data")
    public ResponseEntity<List<RoomDTO>> createMultipleRooms(
            @RequestBody @Valid MultipleRoomsDTO createMultipleRoomsDTO) {
        log.info("Received request to create multiple rooms starting from room number: {} with {} rooms",
                createMultipleRoomsDTO.getStartRoomNumber(), createMultipleRoomsDTO.getNumberOfRooms());

        List<RoomDTO> createdRooms = roomService.createMultipleRooms(
                createMultipleRoomsDTO.getStartRoomNumber(),
                createMultipleRoomsDTO.getNumberOfRooms(),
                createMultipleRoomsDTO.getPrice(),
                createMultipleRoomsDTO.getType()
        );
        log.info("{} rooms created successfully.", createdRooms.size());

        return ResponseEntity.ok(createdRooms);
    }

    @GetMapping
    @Operation(summary = "Get all rooms", description = "Retrieve a list of all rooms")
    @ApiResponse(responseCode = "200", description = "List of rooms")
    public ResponseEntity<List<RoomDTO>> getRooms() {
        log.info("Fetching all rooms");
        List<RoomDTO> rooms = roomService.getAllRoomsDTO();
        log.info("Found {} rooms", rooms.size());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID", description = "Retrieve room details by its unique ID")
    @ApiResponse(responseCode = "200", description = "Room found")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<RoomDTO> getRoom(
            @PathVariable @Parameter(description = "Room ID") Long id) {
        log.info("Fetching room with ID: {}", id);
        RoomDTO room = roomService.getRoom(id);
        if (room != null) {
            log.info("Room found: {}", room);
        } else {
            log.warn("Room with ID {} not found", id);
        }
        return ResponseEntity.ok(room);    }

    @PutMapping("/private/{id}")
    @Operation(summary = "Update room details", description = "Update the details of a specific room")
    @ApiResponse(responseCode = "200", description = "Room updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid room data")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<RoomDTO> updateRoom(
            @PathVariable @Parameter(description = "Room ID") Long id,
            @RequestBody @Valid @Parameter(description = "Updated room details") RoomDTO updatedRoomDTO) {
        log.info("Updating room with ID: {}. New data: {}", id, updatedRoomDTO);
        RoomDTO updatedRoom = roomService.updateRoom(id, updatedRoomDTO);
        log.info("Room updated successfully: {}", updatedRoom);
        return ResponseEntity.ok(updatedRoom);    }

    @DeleteMapping("/private/{id}")
    @Operation(summary = "Delete room", description = "Delete a room by its unique ID")
    @ApiResponse(responseCode = "204", description = "Room deleted successfully")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<Void> deleteRoom(@PathVariable @Parameter(description = "Room ID") Long id) {
        log.info("Deleting room with ID: {}", id);
        roomService.deleteRoom(id);
        log.info("Room with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/private/delete-all")
    @Operation(summary = "Delete all rooms", description = "Deletes all rooms from the system")
    @ApiResponse(responseCode = "204", description = "All rooms deleted successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteAllRooms() {
        try {
            log.info("Received request to delete all rooms.");
            roomService.deleteAllRooms();
            log.info("All rooms deleted successfully.");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting all rooms: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search rooms with criteria", description = "Search for rooms based on criteria like price and availability")
    @ApiResponse(responseCode = "200", description = "List of rooms based on search criteria")
    public ResponseEntity<List<RoomDTO>> getRoomsBySearch(
            @ModelAttribute @Parameter(description = "Search criteria for rooms") RoomSearchCriteria criteria,
            @PageableDefault(size = 5) @Parameter(description = "Pagination details") Pageable pageable) {
        log.info("Searching for rooms with criteria: {} and pagination: {}", criteria, pageable);
        List<RoomDTO> rooms = roomService.getAllRooms(criteria, pageable);
        log.info("Found {} rooms matching criteria", rooms.size());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}/availability")
    @Operation(summary = "Get room availability", description = "Fetches unavailable dates for a specific room")
    @ApiResponse(responseCode = "200", description = "List of dates unavailable for the room reservation")
    public ResponseEntity<List<LocalDate>> getRoomAvailability(@PathVariable @Parameter(description = "Room ID") Long id) {
        log.info("Fetching room availability with ID: {}", id);
        List<LocalDate> unavailableDates = reservationService.getUnavailableDatesForRoom(id);
        log.info("Found {} unavailable dates", unavailableDates.size());
        return ResponseEntity.ok(unavailableDates);
    }

    @PostMapping("/private/{roomId}/amenities/{amenityId}")
    @Operation(summary = "Assign an amenity to a room", description = "Assign a specific amenity to a room based on room and amenity IDs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Amenity successfully assigned to room"),
                    @ApiResponse(responseCode = "404", description = "Room or Amenity not found")
            }
    )
    public ResponseEntity<Void> assignAmenity(@PathVariable Long roomId, @PathVariable Long amenityId) {
        roomAmenityService.assignAmenityToRoom(roomId, amenityId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roomId}/amenities")
    @Operation(summary = "Get all amenities of a room", description = "Fetch all amenities assigned to a specific room",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of amenities for the room"),
                    @ApiResponse(responseCode = "404", description = "Room not found")
            }
    )
    public ResponseEntity<List<AmenityDTO>> getRoomAmenities(@PathVariable Long roomId) {
        List<AmenityDTO> amenities = roomAmenityService.getAmenitiesByRoom(roomId);
        return ResponseEntity.ok(amenities);
    }

    @DeleteMapping("/private/{roomId}/amenities/{amenityId}")
    @Operation(summary = "Remove an amenity from a room", description = "Remove a specific amenity from a room by providing the room and amenity IDs",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Amenity successfully removed from room"),
                    @ApiResponse(responseCode = "404", description = "Room or Amenity not found")
            }
    )
    public ResponseEntity<Void> removeAmenity(@PathVariable Long roomId, @PathVariable Long amenityId) {
        roomAmenityService.removeAmenityFromRoom(roomId, amenityId);
        return ResponseEntity.ok().build();
    }

}

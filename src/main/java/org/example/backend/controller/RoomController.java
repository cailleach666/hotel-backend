package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backend.criteria.RoomSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.example.backend.dtos.RoomDTO;
import org.example.backend.service.RoomService;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
@Tag(name = "Room", description = "Operations related to rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    @Operation(summary = "Create a new room", description = "Create a new room and return the room details")
    @ApiResponse(responseCode = "200", description = "Room created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid room data")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody @Parameter(description = "Room details") RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.createRoom(roomDTO));
    }

    @GetMapping
    @Operation(summary = "Get all rooms", description = "Retrieve a list of all rooms")
    @ApiResponse(responseCode = "200", description = "List of rooms")
    public ResponseEntity<List<RoomDTO>> getRooms() {
        List<RoomDTO> rooms = roomService.getAllRoomsDTO();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get room by ID", description = "Retrieve room details by its unique ID")
    @ApiResponse(responseCode = "200", description = "Room found")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<RoomDTO> getRoom(
            @PathVariable @Parameter(description = "Room ID") Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update room details", description = "Update the details of a specific room")
    @ApiResponse(responseCode = "200", description = "Room updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid room data")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<RoomDTO> updateRoom(
            @PathVariable @Parameter(description = "Room ID") Long id,
            @RequestBody @Parameter(description = "Updated room details") RoomDTO updatedRoomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, updatedRoomDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete room", description = "Delete a room by its unique ID")
    @ApiResponse(responseCode = "204", description = "Room deleted successfully")
    @ApiResponse(responseCode = "404", description = "Room not found")
    public ResponseEntity<Void> deleteRoom(@PathVariable @Parameter(description = "Room ID") Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search rooms with criteria", description = "Search for rooms based on criteria like price and availability")
    @ApiResponse(responseCode = "200", description = "List of rooms based on search criteria")
    public ResponseEntity<Page<RoomDTO>> getRooms(
            @ModelAttribute @Parameter(description = "Search criteria for rooms") RoomSearchCriteria criteria,
            @PageableDefault(sort = "price") @Parameter(description = "Pagination details") Pageable pageable) {
        Page<RoomDTO> rooms = roomService.getRooms(criteria, pageable);
        return ResponseEntity.ok(rooms);
    }
}

package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.criteria.RoomSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.example.backend.dtos.RoomDTO;
import org.example.backend.service.RoomService;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        log.info("Received request to create a room: {}", roomDTO);
        RoomDTO createdRoom = roomService.createRoom(roomDTO);
        log.info("Room created successfully: {}", createdRoom);
        return ResponseEntity.ok(createdRoom);
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getRooms() {
        log.info("Fetching all rooms");
        List<RoomDTO> rooms = roomService.getAllRoomsDTO();
        log.info("Found {} rooms", rooms.size());
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        log.info("Fetching room with ID: {}", id);
        RoomDTO room = roomService.getRoom(id);
        if (room != null) {
            log.info("Room found: {}", room);
        } else {
            log.warn("Room with ID {} not found", id);
        }
        return ResponseEntity.ok(room);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO updatedRoomDTO) {
        log.info("Updating room with ID: {}. New data: {}", id, updatedRoomDTO);
        RoomDTO updatedRoom = roomService.updateRoom(id, updatedRoomDTO);
        log.info("Room updated successfully: {}", updatedRoom);
        return ResponseEntity.ok(updatedRoom);    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        log.info("Deleting room with ID: {}", id);
        roomService.deleteRoom(id);
        log.info("Room with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<RoomDTO>> getRooms(
            @ModelAttribute RoomSearchCriteria criteria,
            @PageableDefault(sort = "price")Pageable pageable) {
        log.info("Searching for rooms with criteria: {} and pagination: {}", criteria, pageable);
        Page<RoomDTO> rooms = roomService.getRooms(criteria, pageable);
        log.info("Found {} rooms matching criteria", rooms.getTotalElements());
        return ResponseEntity.ok(rooms);
    }
}

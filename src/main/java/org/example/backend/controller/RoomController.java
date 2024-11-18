package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.RoomDTO;
import org.example.backend.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO roomDTO) {
        return ResponseEntity.ok(roomService.createRoom(roomDTO));
    }

    @GetMapping
    public ResponseEntity<List<RoomDTO>> getRooms() {
        List<RoomDTO> rooms = roomService.getAllRoomsDTO();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO updatedRoomDTO) {
        return ResponseEntity.ok(roomService.updateRoom(id, updatedRoomDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}

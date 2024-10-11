package org.example.backend.controller;

import org.example.backend.model.Room;
import org.example.backend.model.RoomDTO;
import org.example.backend.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody RoomDTO roomDTO) {
        Room room = new Room();
        room.setRoomnumber(roomDTO.getRoomnumber());
        room.setPrice(roomDTO.getPrice());
        room.setType(roomDTO.getType());
        room.setAvailable(roomDTO.isAvailable());
        room.setType(roomDTO.getType());
        return ResponseEntity.ok(roomService.addRoom(room));
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return ResponseEntity.status(200).body(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoom(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        return ResponseEntity.ok(roomService.updateRoom(id, room));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}

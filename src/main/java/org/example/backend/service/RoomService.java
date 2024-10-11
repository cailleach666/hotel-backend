package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.Room;
import org.example.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoom(Long id) {
        return roomRepository.findById(id).orElseThrow();
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room updateRoom(Long id, Room room) {
        Room roomToUpdate = roomRepository.findById(id).orElseThrow();
        roomToUpdate.setType(room.getType());
        roomToUpdate.setPrice(room.getPrice());
        roomToUpdate.setAvailable(room.isAvailable());
        roomToUpdate.setType(room.getType());
        return roomRepository.save(roomToUpdate);
    }

    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException();
        }
        roomRepository.deleteById(id);
    }
}

package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.RoomDTO;
import org.example.backend.exception.exceptions.NoSuchRoomException;
import org.example.backend.mappers.RoomMapper;
import org.example.backend.model.Room;
import org.example.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomDTO createRoom(RoomDTO roomDTO) {
        return roomMapper.toRoomDto(roomRepository.save(roomMapper.toRoom(roomDTO)));
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchRoomException("Room not found!"));
    }

    public RoomDTO getRoom(Long id) {
        Room room = getRoomById(id);
        return roomMapper.toRoomDto(room);
    }

    public List<Room> getAllRoomsEntity() {
        return roomRepository.findAll();
    }

    public List<RoomDTO> getAllRoomsDTO() {
        return roomMapper.toRoomDTOList(getAllRoomsEntity());
    }

    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        Room room = getRoomById(id);
        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        room.setType(roomDTO.getType());
        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }
}

package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.criteria.RoomSearchCriteria;
import org.example.backend.dtos.RoomDTO;
import org.example.backend.exception.exceptions.NoSuchRoomException;
import org.example.backend.exception.exceptions.RoomNumberAlreadyExistsException;

import org.example.backend.mappers.RoomMapper;
import org.example.backend.model.Room;
import org.example.backend.repository.RoomRepository;

import org.example.backend.specifications.RoomSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomDTO createRoom(RoomDTO roomDTO) {
        log.info("Creating room with number: {}", roomDTO.getRoomNumber());
        validateRoomNumber(roomDTO.getRoomNumber(), null);
        Room room = roomMapper.toRoom(roomDTO);
        Room savedRoom = roomRepository.save(room);
        log.info("Room created successfully with ID: {}", savedRoom.getId());
        return roomMapper.toRoomDto(savedRoom);
    }

    public Room getRoomById(Long id) {
        log.info("Fetching room with ID: {}", id);
        return roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchRoomException("Room not found!"));
    }

    private void validateRoomNumber(String roomNumber, Long roomId) {
        log.info("Validating room number: {}", roomNumber);
        if (roomRepository.existsByRoomNumber(roomNumber)) {
            if (roomId == null || (roomId != null && !roomRepository.findById(roomId).get().getRoomNumber().equals(roomNumber))) {
                throw new RoomNumberAlreadyExistsException("Room with number " + roomNumber + " already exists.");
            }
        }
    }

    public RoomDTO getRoom(Long id) {
        log.info("Fetching room: {}", id);
        Room room = getRoomById(id);
        return roomMapper.toRoomDto(room);
    }

    public List<Room> getAllRoomsEntity() {
        log.info("Fetching all rooms.");
        return roomRepository.findAll();
    }

    public List<RoomDTO> getAllRoomsDTO() {
        log.info("Fetching all rooms in DTO format.");
        return roomMapper.toRoomDTOList(getAllRoomsEntity());
    }

    public RoomDTO updateRoom(Long id, RoomDTO roomDTO) {
        log.info("Updating room with ID: {}", id);
        Room room = getRoomById(id);
        validateRoomNumber(roomDTO.getRoomNumber(), id);

        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        room.setType(roomDTO.getType());
        room.setDescription(roomDTO.getDescription());
        Room updatedRoom = roomRepository.save(room);
        log.info("Room with ID: {} updated successfully.", updatedRoom.getId());
        return roomMapper.toRoomDto(updatedRoom);
    }

    public void deleteRoom(Long id) {
        log.info("Deleting room with ID: {}", id);
        Room room = getRoomById(id);
        roomRepository.delete(room);
        log.info("Room with ID: {} deleted successfully.", id);
    }

    public Page<RoomDTO> getRooms(RoomSearchCriteria criteria, Pageable pageable) {
        log.info("Fetching rooms with criteria: {}", criteria);

        Specification<Room> spec = Specification.where(null);

        if (criteria.getType() != null) {
            spec = spec.and(RoomSpecifications.hasType(criteria.getType()));
        }
        if (criteria.getMinPrice() != null) {
            spec = spec.and(RoomSpecifications.hasPriceGreaterThanOrEqualTo(criteria.getMinPrice()));
        }
        if (criteria.getMaxPrice() != null) {
            spec = spec.and(RoomSpecifications.hasPriceLessThanOrEqualTo(criteria.getMaxPrice()));
        }
        if (criteria.getAvailable() != null) {
            spec = spec.and(RoomSpecifications.isAvailable(criteria.getAvailable()));
        }

        Page<Room> roomPage = roomRepository.findAll(spec, pageable);
        log.info("Found {} rooms based on the given criteria.", roomPage.getTotalElements());
        return roomPage.map(roomMapper::toRoomDto);
    }
}

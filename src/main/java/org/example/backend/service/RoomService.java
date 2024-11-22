package org.example.backend.service;

import lombok.RequiredArgsConstructor;
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


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomDTO createRoom(RoomDTO roomDTO) {
        validateRoomNumber(roomDTO.getRoomNumber(), null);
        return roomMapper.toRoomDto(roomRepository.save(roomMapper.toRoom(roomDTO)));
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchRoomException("Room not found!"));
    }

    private void validateRoomNumber(String roomNumber, Long roomId) {
        if (roomRepository.existsByRoomNumber(roomNumber)) {
            if (roomId == null || (roomId != null && !roomRepository.findById(roomId).get().getRoomNumber().equals(roomNumber))) {
                throw new RoomNumberAlreadyExistsException("Room with number " + roomNumber + " already exists.");
            }
        }
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
        validateRoomNumber(roomDTO.getRoomNumber(), id);

        room.setRoomNumber(roomDTO.getRoomNumber());
        room.setPrice(roomDTO.getPrice());
        room.setAvailable(roomDTO.isAvailable());
        room.setType(roomDTO.getType());
        room.setDescription(roomDTO.getDescription());
        return roomMapper.toRoomDto(roomRepository.save(room));
    }

    public void deleteRoom(Long id) {
        Room room = getRoomById(id);
        roomRepository.delete(room);
    }

    public Page<RoomDTO> getRooms(RoomSearchCriteria criteria, Pageable pageable) {

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
        return roomPage.map(roomMapper::toRoomDto);
    }

}

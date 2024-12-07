package org.example.backend.service.room;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dtos.AmenityDTO;
import org.example.backend.exception.exceptions.AmenityNameAlreadyExistsException;
import org.example.backend.exception.exceptions.NoSuchAmenityException;
import org.example.backend.mappers.AmenityMapper;
import org.example.backend.model.Amenity;
import org.example.backend.model.Room;
import org.example.backend.repository.room.AmenityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AmenityService {

    private final AmenityRepository amenityRepository;
    private final AmenityMapper amenityMapper;

    private final RoomAmenityService roomAmenityService;

    public AmenityDTO createAmenity(AmenityDTO amenityDTO) {
        log.info("Creating amenity with name: {}", amenityDTO.getName());
        validateAmenityName(amenityDTO.getName(), null);
        Amenity amenity = amenityMapper.toAmenity(amenityDTO);
        Amenity savedAmenity = amenityRepository.save(amenity);
        log.info("Amenity created successfully with ID: {}", savedAmenity.getId());
        return amenityMapper.toAmenityDTO(savedAmenity);
    }

    public Amenity getAmenityById(Long id) {
        log.info("Fetching amenity with ID: {}", id);
        return amenityRepository.findById(id)
                .orElseThrow(() -> new NoSuchAmenityException("Amenity not found!"));
    }

    public AmenityDTO getAmenity(Long id) {
        log.info("Fetching amenity DTO with ID: {}", id);
        Amenity amenity = getAmenityById(id);
        return amenityMapper.toAmenityDTO(amenity);
    }

    public List<AmenityDTO> getAllAmenities() {
        log.info("Fetching all amenities.");
        List<Amenity> amenities = amenityRepository.findAll();
        return amenityMapper.toAmenityDTOList(amenities);
    }

    public AmenityDTO updateAmenity(Long id, AmenityDTO amenityDTO) {
        log.info("Updating amenity with ID: {}", id);
        Amenity existingAmenity = getAmenityById(id);
        validateAmenityName(amenityDTO.getName(), id);

        existingAmenity.setName(amenityDTO.getName());
        existingAmenity.setDescription(amenityDTO.getDescription());
        existingAmenity.setAdditionalCost(amenityDTO.getAdditionalCost());

        Amenity updatedAmenity = amenityRepository.save(existingAmenity);
        log.info("Amenity with ID: {} updated successfully.", updatedAmenity.getId());
        return amenityMapper.toAmenityDTO(updatedAmenity);
    }

    public void deleteAmenity(Long id) {
        log.info("Attempting to delete amenity with ID: {}", id);
        Amenity amenity = getAmenityById(id);

        log.info("Removing amenity with ID: {} from all associated rooms.", id);
        for (Room room : amenity.getRooms()) {
            log.info("Removing amenity from room with ID: {}", room.getId());
            roomAmenityService.removeAmenityFromRoom(room.getId(), id);
        }

        amenityRepository.delete(amenity);
        log.info("Amenity with ID: {} deleted successfully.", id);
    }

    private void validateAmenityName(String name, Long amenityId) {
        log.info("Validating amenity name: {}", name);
        if (amenityRepository.existsByName(name) && (amenityId == null || !amenityRepository.findById(amenityId).get().getName().equals(name))) {
            throw new AmenityNameAlreadyExistsException("Amenity with name '" + name + "' already exists.");
        }
    }
}

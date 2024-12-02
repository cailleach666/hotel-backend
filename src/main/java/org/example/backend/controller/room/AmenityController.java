package org.example.backend.controller.room;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dtos.AmenityDTO;
import org.example.backend.service.room.AmenityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/amenities")
@Tag(name = "Amenity", description = "Operations related to amenities")
public class AmenityController {

    private final AmenityService amenityService;

    @PostMapping
    @Operation(summary = "Create a new amenity", description = "Create a new amenity and return the amenity details")
    @ApiResponse(responseCode = "200", description = "Amenity created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid amenity data")
    public ResponseEntity<AmenityDTO> createAmenity(
            @RequestBody @Valid @Parameter(description = "Amenity details") AmenityDTO amenityDTO) {
        log.info("Received request to create an amenity: {}", amenityDTO);
        AmenityDTO createdAmenity = amenityService.createAmenity(amenityDTO);
        log.info("Amenity created successfully: {}", createdAmenity);
        return ResponseEntity.ok(createdAmenity);
    }

    @GetMapping
    @Operation(summary = "Get list of amenities", description = "Retrieve a list of all amenities")
    @ApiResponse(responseCode = "200", description = "List of amenities")
    public ResponseEntity<List<AmenityDTO>> getAmenities() {
        log.info("Fetching all amenities");
        List<AmenityDTO> amenities = amenityService.getAllAmenities();
        log.info("Found {} amenities", amenities.size());
        return ResponseEntity.ok(amenities);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get amenity by ID", description = "Retrieve an amenity by its unique ID")
    @ApiResponse(responseCode = "200", description = "Amenity found")
    @ApiResponse(responseCode = "404", description = "Amenity not found")
    public ResponseEntity<AmenityDTO> getAmenity(
            @PathVariable @Parameter(description = "Amenity ID") Long id) {
        log.info("Fetching amenity with ID: {}", id);
        AmenityDTO amenity = amenityService.getAmenity(id);
        if (amenity != null) {
            log.info("Amenity found: {}", amenity);
        } else {
            log.warn("Amenity with ID {} not found", id);
        }
        return ResponseEntity.ok(amenity);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update amenity details", description = "Update the details of a specific amenity")
    @ApiResponse(responseCode = "200", description = "Amenity updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid amenity data")
    @ApiResponse(responseCode = "404", description = "Amenity not found")
    public ResponseEntity<AmenityDTO> updateAmenity(
            @PathVariable @Parameter(description = "Amenity ID") Long id,
            @RequestBody @Valid @Parameter(description = "Updated amenity details") AmenityDTO updatedAmenityDTO) {
        log.info("Updating amenity with ID: {}. New data: {}", id, updatedAmenityDTO);
        AmenityDTO updatedAmenity = amenityService.updateAmenity(id, updatedAmenityDTO);
        log.info("Amenity updated successfully: {}", updatedAmenity);
        return ResponseEntity.ok(updatedAmenity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an amenity", description = "Delete an amenity by its unique ID")
    @ApiResponse(responseCode = "204", description = "Amenity deleted successfully")
    @ApiResponse(responseCode = "404", description = "Amenity not found")
    public ResponseEntity<Void> deleteAmenity(@PathVariable @Parameter(description = "Amenity ID") Long id) {
        log.info("Deleting amenity with ID: {}", id);
        amenityService.deleteAmenity(id);
        log.info("Amenity with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}

package org.example.backend.mappers;

import org.example.backend.dtos.AmenityDTO;
import org.example.backend.model.Amenity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AmenityMapper {

    AmenityDTO toAmenityDTO(Amenity amenity);
    Amenity toAmenity(AmenityDTO amenityDTO);

    List<AmenityDTO> toAmenityDTOList(List<Amenity> amenityList);
    List<Amenity> toAmenityList(List<AmenityDTO> amenityDTOList);
}

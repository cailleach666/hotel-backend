package org.example.backend.mappers;

import org.example.backend.dtos.ReservationDTO;
import org.example.backend.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    @Mapping(source = "clientId.id", target = "clientId")
    @Mapping(source = "roomId.id", target = "roomId")
    ReservationDTO toReservationDto(Reservation reservation);

    @Mapping(source = "clientId", target = "clientId.id")
    @Mapping(source = "roomId", target = "roomId.id")
    Reservation toReservation(ReservationDTO reservationDTO);

    List<ReservationDTO> toReservationDTOList(List<Reservation> reservationList);
    List<Reservation> toReservationList(List<ReservationDTO> reservationDTOList);
}

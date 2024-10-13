package org.example.backend.mappers;

import org.example.backend.dtos.ReservationDTO;
import org.example.backend.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {

    ReservationDTO toReservationDto(Reservation reservation);
    Reservation toReservation(ReservationDTO reservationDTO);

    List<ReservationDTO> toReservationDTOList(List<Reservation> reservationList);
    List<Reservation> toReservationList(List<ReservationDTO> reservationDTOList);
}

package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.ReservationDTO;
import org.example.backend.enums.RoomType;
import org.example.backend.exception.exceptions.InvalidNumberOfGuestsException;
import org.example.backend.exception.exceptions.NoSuchClientException;
import org.example.backend.exception.exceptions.NoSuchReservationException;
import org.example.backend.exception.exceptions.NoSuchRoomException;
import org.example.backend.mappers.ReservationMapper;
import org.example.backend.model.Client;
import org.example.backend.model.Reservation;
import org.example.backend.model.Room;
import org.example.backend.repository.ClientRepository;
import org.example.backend.repository.ReservationRepository;
import org.example.backend.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Client client = getClientById(reservationDTO.getClientId());

        Room room = getRoomById(reservationDTO.getRoomId());

        validateNumberOfGuests(room, reservationDTO.getNumberOfGuests());

        Reservation reservation = reservationMapper.toReservation(reservationDTO);
        reservation.setClientId(client);
        reservation.setRoomId(room);
        reservation.setTotalPrice(calculateTotalPrice(reservation));

        Reservation savedReservation = reservationRepository.save(reservation);
        return reservationMapper.toReservationDto(savedReservation);
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchReservationException("Reservation not found!"));
    }

    public Room getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchRoomException("Room not found!"));
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("Client not found!"));
    }

    public ReservationDTO getReservation(Long id) {
        Reservation reservation = getReservationById(id);
        return reservationMapper.toReservationDto(reservation);
    }

    public List<Reservation> getAllReservationsEntity() {
        return reservationRepository.findAll();
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationMapper.toReservationDTOList(getAllReservationsEntity());
    }

    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        Reservation reservation = getReservationById(id);
        Client client = getClientById(reservationDTO.getClientId());
        Room room = getRoomById(reservationDTO.getRoomId());

        validateNumberOfGuests(room, reservationDTO.getNumberOfGuests());

        reservation.setClientId(client);
        reservation.setRoomId(room);
        reservation.setCheckInDate(reservationDTO.getCheckInDate());
        reservation.setCheckOutDate(reservationDTO.getCheckOutDate());
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setTotalPrice(calculateTotalPrice(reservation));

        Reservation updatedReservation = reservationRepository.save(reservation);
        return reservationMapper.toReservationDto(updatedReservation);
    }

    public void deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
    }

    private long calculateDays(Reservation reservation) {
        return ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
    }

    private Double calculateTotalPrice(Reservation reservation) {
        long days = calculateDays(reservation);
        Room room = roomRepository.findById(reservation.getRoomId().getId())
                .orElseThrow(() ->  new NoSuchRoomException("Room not found!"));
        return room.getPrice() * days;
    }

    /**
     * Helper method to validate the number of guests based on the room type.
     * @param room the room to validate against
     * @param numberOfGuests the number of guests to validate
     */
    private void validateNumberOfGuests(Room room, Long numberOfGuests) {
        if (numberOfGuests < 1) {
            throw new InvalidNumberOfGuestsException("There must be at least 1 guest.");
        }

        RoomType roomType = room.getType();

        if (roomType == RoomType.SINGLE && numberOfGuests != 1) {
            throw new InvalidNumberOfGuestsException("For a SINGLE room, only 1 guest is allowed.");
        }

        if (roomType == RoomType.DOUBLE && (numberOfGuests < 1 || numberOfGuests > 2)) {
            throw new InvalidNumberOfGuestsException("For a DOUBLE room, the number of guests must be between 1 and 2.");
        }

        if (roomType == RoomType.DELUXE && (numberOfGuests < 1 || numberOfGuests > 5)) {
            throw new InvalidNumberOfGuestsException("For a DELUXE room, the number of guests must be between 1 and 5.");
        }
    }
}

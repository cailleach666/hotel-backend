package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RoomRepository roomRepository;
    private final ClientRepository clientRepository;

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        log.info("Creating reservation for client with ID: {} for room with ID: {}", reservationDTO.getClientId(), reservationDTO.getRoomId());

        Client client = getClientById(reservationDTO.getClientId());

        Room room = getRoomById(reservationDTO.getRoomId());

        validateNumberOfGuests(room, reservationDTO.getNumberOfGuests());

        validateReservationDates(reservationDTO.getCheckInDate(), reservationDTO.getCheckOutDate());

        Reservation reservation = reservationMapper.toReservation(reservationDTO);
        reservation.setClientId(client);
        reservation.setRoomId(room);
        reservation.setTotalPrice(calculateTotalPrice(reservation));

        Reservation savedReservation = reservationRepository.save(reservation);
        log.info("Reservation created successfully with ID: {}", savedReservation.getId());
        return reservationMapper.toReservationDto(savedReservation);
    }

    public List<ReservationDTO> getReservationsByClientId(Long clientId) {
        log.info("Fetching reservations for client with ID: {}", clientId);

        Client client = getClientById(clientId); // Reuse existing method to get the client by ID
        List<Reservation> reservations = reservationRepository.findByClientId(client); // You need to implement this query in the repository
        log.info("Found {} reservations for client with ID: {}", reservations.size(), clientId);
        return reservationMapper.toReservationDTOList(reservations); // Map to DTO list
    }

    private void validateReservationDates(LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            log.error("Check-in date or check-out date is null.");
            throw new IllegalArgumentException("Check-in date and check-out date must not be null.");
        }

        if (!checkInDate.isBefore(checkOutDate)) {
            log.error("Check-in date {} is not before check-out date {}", checkInDate, checkOutDate);
            throw new IllegalArgumentException("Check-in date must be before the check-out date.");
        }
    }

    public Reservation getReservationById(Long id) {
        log.info("Fetching reservation with ID: {}", id);
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NoSuchReservationException("Reservation not found!"));
    }

    public Room getRoomById(Long id) {
        log.info("Fetching room with ID: {}", id);
        return roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchRoomException("Room not found!"));
    }

    public Client getClientById(Long id) {
        log.info("Fetching client with ID: {}", id);
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("Client not found!"));
    }

    public ReservationDTO getReservation(Long id) {
        Reservation reservation = getReservationById(id);
        log.info("Fetched reservation with ID: {}", id);
        return reservationMapper.toReservationDto(reservation);
    }

    public List<Reservation> getAllReservationsEntity() {
        return reservationRepository.findAll();
    }

    public List<ReservationDTO> getAllReservations() {
        log.info("Fetching all reservations.");
        return reservationMapper.toReservationDTOList(getAllReservationsEntity());
    }

    public ReservationDTO updateReservation(Long id, ReservationDTO reservationDTO) {
        log.info("Updating reservation with ID: {}", id);
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
        log.info("Reservation with ID: {} updated successfully.", updatedReservation.getId());
        return reservationMapper.toReservationDto(updatedReservation);
    }

    public void deleteReservation(Long id) {
        log.info("Deleting reservation with ID: {}", id);
        Reservation reservation = getReservationById(id);
        reservationRepository.delete(reservation);
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
            log.error("Invalid number of guests: {}", numberOfGuests);
            throw new InvalidNumberOfGuestsException("There must be at least 1 guest.");
        }

        RoomType roomType = room.getType();

        if (roomType == RoomType.SINGLE && numberOfGuests != 1) {
            log.error("Invalid number of guests for SINGLE room: {}", numberOfGuests);
            throw new InvalidNumberOfGuestsException("For a SINGLE room, only 1 guest is allowed.");
        }

        if (roomType == RoomType.DOUBLE && (numberOfGuests < 1 || numberOfGuests > 2)) {
            log.error("Invalid number of guests for DOUBLE room: {}", numberOfGuests);
            throw new InvalidNumberOfGuestsException("For a DOUBLE room, the number of guests must be between 1 and 2.");
        }

        if (roomType == RoomType.TWIN && (numberOfGuests < 1 || numberOfGuests > 2)) {
            log.error("Invalid number of guests for TWIN room: {}", numberOfGuests);
            throw new InvalidNumberOfGuestsException("For a TWIN room, the number of guests must be between 1 and 2.");
        }

        if (roomType == RoomType.DELUXE && (numberOfGuests < 1 || numberOfGuests > 5)) {
            log.error("Invalid number of guests for DELUXE room: {}", numberOfGuests);
            throw new InvalidNumberOfGuestsException("For a DELUXE room, the number of guests must be between 1 and 5.");
        }
    }
}

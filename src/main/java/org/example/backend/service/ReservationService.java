package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.Reservation;
import org.example.backend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow();
    }

    public Reservation updateReservation(Long id, Reservation reservation) {
        Reservation reservationToUpdate = reservationRepository.findById(id).orElseThrow();
        reservationToUpdate.setCheckindate(reservation.getCheckindate());
        reservationToUpdate.setCheckoutdate(reservation.getCheckoutdate());
        return reservationRepository.save(reservationToUpdate);
    }

    public Reservation updateStatus(Long id, Reservation reservation) {
        Reservation reservationToUpdate = reservationRepository.findById(id).orElseThrow();
        reservationToUpdate.setStatus(reservation.getStatus());
        return reservationRepository.save(reservationToUpdate);
    }

    public void deleteReservation(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new RuntimeException();
        }
        reservationRepository.deleteById(id);
    }
}

package org.example.backend.repository.reservation;

import org.example.backend.model.Client;
import org.example.backend.model.Reservation;
import org.example.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientId(Client client);

    List<Reservation> findByRoomId(Room room);
}

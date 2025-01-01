package org.example.backend;

import org.example.backend.dtos.ClientDTO;
import org.example.backend.dtos.ReservationDTO;
import org.example.backend.dtos.RoomDTO;
import org.example.backend.enums.RoomType;
import org.example.backend.mappers.RoomMapper;
import org.example.backend.model.Client;
import org.example.backend.model.Reservation;
import org.example.backend.model.Room;
import org.example.backend.repository.client.ClientRepository;
import org.example.backend.repository.reservation.ReservationRepository;
import org.example.backend.repository.room.RoomCriteriaRepository;
import org.example.backend.repository.room.RoomRepository;
import org.example.backend.service.room.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class BaseTests {

    @Mock
    public ClientRepository clientRepository;

    @Mock
    public RoomRepository roomRepository;

    @Mock
    public ReservationRepository reservationRepository;

    @Mock
    public RoomCriteriaRepository roomCriteriaRepository;

    @Mock
    public RoomMapper roomMapper;

    @InjectMocks
    public RoomService roomService;

    public ClientDTO clientDTO;
    public RoomDTO roomDTO;
    public ReservationDTO reservationDTO;

    public Client client;
    public Room room;
    public Reservation reservation;

    @BeforeEach
    void setUp() {
        roomDTO = new RoomDTO();
        roomDTO.setRoomNumber("101");
        roomDTO.setPrice(150.00);
        roomDTO.setAvailable(true);
        roomDTO.setType(RoomType.SINGLE);
        roomDTO.setDescription("A comfortable room.");

        room = new Room();
        room.setRoomNumber("101");
        room.setPrice(150.00);
        room.setAvailable(true);
        room.setType(RoomType.SINGLE);
        room.setDescription("A comfortable room.");
    }
}

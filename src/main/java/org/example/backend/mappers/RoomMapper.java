package org.example.backend.mappers;

import org.example.backend.dtos.RoomDTO;
import org.example.backend.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    RoomDTO toRoomDto(Room room);
    Room toRoom(RoomDTO roomDTO);

    List<RoomDTO> toRoomDTOList(List<Room> roomList);
    List<Room> toRoomList(List<RoomDTO> roomDTOList);
}

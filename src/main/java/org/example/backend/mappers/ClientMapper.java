package org.example.backend.mappers;

import org.example.backend.dtos.ClientDTO;
import org.example.backend.dtos.auth.RegisterRequestDTO;
import org.example.backend.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientDTO toClientDto(Client client);
    Client toClient(ClientDTO clientDTO);
    Client toClient(RegisterRequestDTO registerRequestDTO);

    List<ClientDTO> toClientDTOList(List<Client> clientList);
    List<Client> toClientList(List<ClientDTO> clientDTOList);
}

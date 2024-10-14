package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.ClientDTO;
import org.example.backend.exception.exceptions.NoSuchClientException;
import org.example.backend.mappers.ClientMapper;
import org.example.backend.model.Client;
import org.example.backend.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDTO createClient(ClientDTO clientDTO) {
        return clientMapper.toClientDto(clientRepository.save(clientMapper.toClient(clientDTO)));
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("Client not found!"));
    }

    public ClientDTO getClient(Long id) {
        Client client = getClientById(id);
        return clientMapper.toClientDto(client);
    }

    public List<Client> getAllClientsEntity() {
        return clientRepository.findAll();
    }

    public List<ClientDTO> getAllClientsDTO() {
        return clientMapper.toClientDTOList(getAllClientsEntity());
    }

    public ClientDTO updateClient(Long id, ClientDTO clientDTO) {
        Client client = getClientById(id);
        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setPhone(clientDTO.getPhone());
        return clientMapper.toClientDto(clientRepository.save(client));
    }

    public void deleteClient(Long id) {
        Client client = getClientById(id);
        clientRepository.delete(client);
    }
}

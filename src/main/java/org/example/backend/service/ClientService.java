package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dtos.ClientDTO;
import org.example.backend.exception.exceptions.ClientEmailAlreadyExistsException;
import org.example.backend.exception.exceptions.NoSuchClientException;
import org.example.backend.mappers.ClientMapper;
import org.example.backend.model.Client;
import org.example.backend.repository.ClientRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
//    private final BCryptPasswordEncoder passwordEncoder;

    public ClientDTO createClient(ClientDTO clientDTO) {
        log.info("Creating a new client with email: {}", clientDTO.getEmail());

        validateClientEmail(clientDTO.getEmail(), null);
        Client client = clientMapper.toClient(clientDTO);
//        client.setPassword(passwordEncoder.encode(client.getPassword()));

        Client savedClient = clientRepository.save(client);
        log.info("Client with email: {} created successfully", clientDTO.getEmail());
        return clientMapper.toClientDto(savedClient);
    }

    public ClientDTO getClientByEmail(String email) {
        return clientMapper.toClientDto(clientRepository.findByEmail(email));
    }

    public Client getClientById(Long id) {
        log.info("Fetching client by id: {}", id);
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("Client not found!"));
    }

    private void validateClientEmail(String email, Long clientId) {
        if (clientRepository.existsByEmail(email)) {
            if (clientId == null || !clientRepository.findById(clientId).get().getEmail().equals(email)) {
                throw new ClientEmailAlreadyExistsException("Account with email " + email + " already exists.");
            }
        }
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
        log.info("Updating client with id: {}", id);

        Client client = getClientById(id);
        validateClientEmail(clientDTO.getEmail(), id);

        client.setFirstName(clientDTO.getFirstName());
        client.setLastName(clientDTO.getLastName());
        client.setEmail(clientDTO.getEmail());
        client.setPhone(clientDTO.getPhone());
        Client updatedClient = clientRepository.save(client);
        log.info("Client with id: {} updated successfully", id);
        return clientMapper.toClientDto(updatedClient);
    }

    public void deleteClient(Long id) {
        log.info("Deleting client with id: {}", id);
        Client client = getClientById(id);
        clientRepository.delete(client);
        log.info("Client with id: {} deleted successfully", id);
    }
}

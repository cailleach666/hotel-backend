package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.ClientDTO;
import org.example.backend.exception.exceptions.ClientEmailAlreadyExistsException;
import org.example.backend.exception.exceptions.NoSuchClientException;
import org.example.backend.mappers.ClientMapper;
import org.example.backend.model.Client;
import org.example.backend.repository.ClientRepository;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
//    private final BCryptPasswordEncoder passwordEncoder;

    public ClientDTO createClient(ClientDTO clientDTO) {
        validateClientEmail(clientDTO.getEmail(), null);
        Client client = clientMapper.toClient(clientDTO);
//        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientMapper.toClientDto(clientRepository.save(client));
    }

    public ClientDTO getClientByEmail(String email) {
        return clientMapper.toClientDto(clientRepository.findByEmail(email)); // Assuming your repository has this method
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchClientException("Client not found!"));
    }

    private void validateClientEmail(String email, Long clientId) {
        if (clientRepository.existsByEmail(email)) {
            if (clientId == null || (clientId != null && !clientRepository.findById(clientId).get().getEmail().equals(email))) {
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
        Client client = getClientById(id);
        validateClientEmail(clientDTO.getEmail(), id);

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

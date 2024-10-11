package org.example.backend.service;

import lombok.RequiredArgsConstructor;
import org.example.backend.model.Client;
import org.example.backend.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;

    public Client registerClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClient(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client updateClient(Long id, Client client) {
        Client clientToUpdate = clientRepository.findById(id).orElseThrow();
        clientToUpdate.setFirstname(client.getFirstname());
        clientToUpdate.setLastname(client.getLastname());
        clientToUpdate.setPhone(client.getPhone());
        return clientRepository.save(clientToUpdate);
    }
}

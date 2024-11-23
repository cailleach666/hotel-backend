package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.ClientDTO;
import org.example.backend.exception.exceptions.NoSuchClientException;
import org.example.backend.model.Client;
import org.example.backend.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.createClient(clientDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<ClientDTO> login(@RequestBody ClientDTO clientToCheck) {
        ClientDTO client = clientService.getClientByEmail(clientToCheck.getEmail());

        if (client != null && client.getPassword().equals(clientToCheck.getPassword())) {
            return ResponseEntity.ok(client);
        } else {
            throw new NoSuchClientException("Incorrect email or password");
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients = clientService.getAllClientsDTO();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO updatedClientDTO) {
        return ResponseEntity.ok(clientService.updateClient(id, updatedClientDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

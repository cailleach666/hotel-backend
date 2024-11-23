package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.dtos.ClientDTO;
import org.example.backend.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        log.info("Received request to create client: {}", clientDTO);
        ClientDTO createdClient = clientService.createClient(clientDTO);
        log.info("Client created successfully: {}", createdClient);
        return ResponseEntity.ok(createdClient);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody ClientDTO clientToCheck) {
        log.info("Login attempt for email: {}", clientToCheck.getEmail());

        ClientDTO client = clientService.getClientByEmail(clientToCheck.getEmail());

        if (client != null && client.getPassword().equals(clientToCheck.getPassword())) {
            log.info("Login successful for email: {}", clientToCheck.getEmail());
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            log.warn("Invalid login attempt for email: {}", clientToCheck.getEmail());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid email or password");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getClients() {
        log.info("Fetching all clients");
        List<ClientDTO> clients = clientService.getAllClientsDTO();
        log.info("Found {} clients", clients.size());
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(@PathVariable Long id) {
        log.info("Fetching client with ID: {}", id);
        ClientDTO client = clientService.getClient(id);
        if (client != null) {
            log.info("Client found: {}", client);
        } else {
            log.warn("Client with ID {} not found", id);
        }
        return ResponseEntity.ok(client);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO updatedClientDTO) {
        log.info("Updating client with ID: {}. New data: {}", id, updatedClientDTO);
        ClientDTO updatedClient = clientService.updateClient(id, updatedClientDTO);
        log.info("Client updated successfully: {}", updatedClient);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.info("Deleting client with ID: {}", id);
        clientService.deleteClient(id);
        log.info("Client with ID {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}

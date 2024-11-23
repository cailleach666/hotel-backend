package org.example.backend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.ClientDTO;
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
@Tag(name = "Client", description = "Operations related to clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @Operation(summary = "Create a new client", description = "Create a new client and return the client details")
    @ApiResponse(responseCode = "200", description = "Client created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid client data")
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Parameter(description = "Client details") ClientDTO clientDTO) {
        return ResponseEntity.ok(clientService.createClient(clientDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Client login", description = "Authenticate a client by email and password")
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid email or password")
    public ResponseEntity<Map<String, String>> login(@RequestBody @Parameter(description = "Client credentials") ClientDTO clientToCheck) {
        ClientDTO client = clientService.getClientByEmail(clientToCheck.getEmail());

        if (client != null && client.getPassword().equals(clientToCheck.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login successful");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid email or password");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }

    @GetMapping
    @Operation(summary = "Get list of clients", description = "Retrieve a list of all clients")
    @ApiResponse(responseCode = "200", description = "List of clients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clients = clientService.getAllClientsDTO();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a client by ID", description = "Retrieve a client by their unique ID")
    @ApiResponse(responseCode = "200", description = "Client found")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<ClientDTO> getClient(@PathVariable @Parameter(description = "Client ID") Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update client details", description = "Update the details of a specific client")
    @ApiResponse(responseCode = "200", description = "Client updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid client data")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable @Parameter(description = "Client ID") Long id,
                                                  @RequestBody @Parameter(description = "Updated client details") ClientDTO updatedClientDTO) {
        return ResponseEntity.ok(clientService.updateClient(id, updatedClientDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a client", description = "Delete a client by their unique ID")
    @ApiResponse(responseCode = "204", description = "Client deleted successfully")
    @ApiResponse(responseCode = "404", description = "Client not found")
    public ResponseEntity<Void> deleteClient(@PathVariable @Parameter(description = "Client ID") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}

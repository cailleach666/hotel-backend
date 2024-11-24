package org.example.backend.service;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.backend.configs.JwtTokenProvider;
import org.example.backend.dtos.auth.LoginRequestDTO;
import org.example.backend.dtos.auth.LoginResponseDTO;
import org.example.backend.dtos.auth.RegisterRequestDTO;
import org.example.backend.exception.exceptions.AuthenticationException;
import org.example.backend.exception.exceptions.ClientEmailAlreadyExistsException;
import org.example.backend.mappers.ClientMapper;
import org.example.backend.model.Client;
import org.example.backend.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;
    private final long TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 10;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Client client = clientRepository.findByEmail(loginRequestDTO.getEmail());

        if (passwordEncoder.matches(loginRequestDTO.getPassword(), client.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", client.getId());
            claims.put("email", client.getEmail());
            String token = getToken(claims);

            LoginResponseDTO responseDTO = new LoginResponseDTO();

            responseDTO.setId(client.getId());
            responseDTO.setToken(token);
            responseDTO.setEmail(client.getEmail());

            return responseDTO;
        } else {
            throw new AuthenticationException("Invalid email or password.");
        }
    }

    public LoginResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        log.info("Registering new client with email: {}", registerRequestDTO.getEmail());

        if (clientRepository.findByEmail(registerRequestDTO.getEmail()) != null) {
            throw new ClientEmailAlreadyExistsException("User with this email already exists.");
        }

        String hashedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
        registerRequestDTO.setPassword(hashedPassword);

        Client newClient = clientMapper.toClient(registerRequestDTO);
        Client savedClient = clientRepository.save(newClient);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", newClient.getId());
        claims.put("email", newClient.getEmail());
        String token = getToken(claims);

        LoginResponseDTO responseDTO = new LoginResponseDTO();

        responseDTO.setId(savedClient.getId());
        responseDTO.setToken(token);
        responseDTO.setEmail(savedClient.getEmail());

        log.info("Client with email: {} registered successfully", savedClient.getEmail());

        return responseDTO;
    }

    public String getToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject("sub")
                .addClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(JwtTokenProvider.key)
                .compact();
    }

}

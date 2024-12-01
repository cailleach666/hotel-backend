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
import org.example.backend.model.Role;
import org.example.backend.repository.ClientRepository;
import org.example.backend.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final Validator validator;
    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClientMapper clientMapper;
    private static final long TOKEN_VALIDITY = (long) 1000 * 60 * 60 * 24 * 10;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Client client = clientRepository.findByEmail(loginRequestDTO.getEmail());

        if (passwordEncoder.matches(loginRequestDTO.getPassword(), client.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", client.getId());
            claims.put("email", client.getEmail());
            claims.put("roles", client.getRoles().stream()
                    .map(Role::getName).toList());
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
        validateRegisterRequest(registerRequestDTO);

        log.info("Registering new client with email: {}", registerRequestDTO.getEmail());

        if (clientRepository.findByEmail(registerRequestDTO.getEmail()) != null) {
            throw new ClientEmailAlreadyExistsException("User with this email already exists.");
        }

        String hashedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
        registerRequestDTO.setPassword(hashedPassword);

        Client newClient = clientMapper.toClient(registerRequestDTO);
        Role role;
        if (Objects.equals(newClient.getEmail(), "admin@example.com")) {
            role = roleRepository.findByName("ROLE_ADMIN");
        }
        else {
            role = roleRepository.findByName("ROLE_USER");
        }
        newClient.setRoles(List.of(role));

        log.info("New client with roles: {}", newClient.getRoles().stream().map(Role::getName).toList());

        Client savedClient = clientRepository.save(newClient);

        Map<String, Object> claims = new HashMap<>();
        claims.put("id", newClient.getId());
        claims.put("email", newClient.getEmail());
        claims.put("roles", newClient.getRoles().stream()
                .map(Role::getName)
                .toList());
        String token = getToken(claims);
        log.info("Claims: {}", claims);

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

    private void validateRegisterRequest(RegisterRequestDTO requestDTO) {
        Set<ConstraintViolation<RegisterRequestDTO>> violations = validator.validate(requestDTO);
        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<RegisterRequestDTO> violation : violations) {
                errorMessages.append(violation.getPropertyPath())
                        .append(": ").append(violation.getMessage()).append("\n");
            }
            throw new IllegalArgumentException("Validation failed: " + errorMessages.toString());
        }
    }

}

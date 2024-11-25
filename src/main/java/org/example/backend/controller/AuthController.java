package org.example.backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.dtos.auth.LoginRequestDTO;
import org.example.backend.dtos.auth.LoginResponseDTO;
import org.example.backend.dtos.auth.RegisterRequestDTO;
import org.example.backend.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public LoginResponseDTO register(@RequestBody RegisterRequestDTO registerRequest) {
        return authService.register(registerRequest);
    }
}

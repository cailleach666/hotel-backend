package org.example.backend.dtos.auth;

import lombok.Data;

@Data
public class LoginResponseDTO {

    private Long id;
    private String email;
    private String token;
}

package org.example.backend.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDTO {

    @Schema(description = "The unique identifier of the client", example = "1")
    private Long id;

    @Schema(description = "The client's first name", example = "John")
    private String firstName;

    @Schema(description = "The client's last name", example = "Doe")
    private String lastName;

    @Schema(description = "The client's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "The client's phone number", example = "+1234567890")
    private String phone;

}

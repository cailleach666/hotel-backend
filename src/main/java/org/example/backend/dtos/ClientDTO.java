package org.example.backend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

}

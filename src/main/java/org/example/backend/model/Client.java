package org.example.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(name = "first_name")
    private String firstname;
    //@Column(name = "last_name")
    private String lastname;
    private String email;
    private String phone;
}

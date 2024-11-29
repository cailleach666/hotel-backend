package org.example.backend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AmenityDTO {

    private Long id;
    private String name;
    private String description;
    private double additionalCost;
}

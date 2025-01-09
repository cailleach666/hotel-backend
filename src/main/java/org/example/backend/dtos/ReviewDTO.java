package org.example.backend.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewDTO {

    private Long id;
    private Long clientId;
    private Long reservationId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}

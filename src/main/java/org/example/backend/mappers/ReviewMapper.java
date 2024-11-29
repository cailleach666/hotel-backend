package org.example.backend.mappers;

import org.example.backend.dtos.ReviewDTO;
import org.example.backend.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(source = "clientId.id", target = "clientId")
    @Mapping(source = "clientId.firstName", target = "clientFirstName")
    @Mapping(source = "clientId.lastName", target = "clientLastName")
    @Mapping(source = "reservationId.id", target = "reservationId")
    @Mapping(source = "createdAt", target = "createdAt")
    ReviewDTO toReviewDTO(Review review);

    @Mapping(source = "clientId", target = "clientId.id")
    @Mapping(source = "reservationId", target = "reservationId.id")
    Review toReview(ReviewDTO reviewDTO);

    List<ReviewDTO> toReviewDTOList(List<Review> reviewList);
    List<Review> toReviewList(List<ReviewDTO> reviewRequestDTOList);
}

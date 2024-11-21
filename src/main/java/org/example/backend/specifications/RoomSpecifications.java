package org.example.backend.specifications;


import org.example.backend.enums.RoomType;
import org.example.backend.model.Room;
import org.springframework.data.jpa.domain.Specification;

public class RoomSpecifications {

    public static Specification<Room> hasType(RoomType type) {
        return (root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Room> hasPriceLessThanOrEqualTo(Double maxPrice) {
        return (root, query, criteriaBuilder) ->
                maxPrice == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    public static Specification<Room> hasPriceGreaterThanOrEqualTo(Double minPrice) {
        return (root, query, criteriaBuilder) ->
                minPrice == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    public static Specification<Room> isAvailable(Boolean available) {
        return (root, query, criteriaBuilder) ->
                available == null ? null : criteriaBuilder.equal(root.get("available"), available);
    }
}

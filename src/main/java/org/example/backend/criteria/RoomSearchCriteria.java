package org.example.backend.criteria;

import lombok.Data;
import org.example.backend.enums.RoomType;

@Data
public class RoomSearchCriteria {
    private RoomType type;
    private Double minPrice;
    private Double maxPrice;
    private Boolean available;
}

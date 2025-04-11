package com.vahana.dtos.v1.rides;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vahana.utils.v1.rides.RideStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Sort;

import java.util.UUID;

@Data
@Accessors(chain = true)
@Schema(
        name = "RideFilter",
        title = "Ride Filter",
        description = "Filter options for retrieving rides."
)
public final class FilterRideDTO {
    @Schema(
            name = "page",
            description = "Page number for pagination.",
            example = "0",
            defaultValue = "0",
            nullable = true
    )
    @JsonProperty("page")
    private int page = 0;

    @Schema(
            name = "size",
            description = "Number of rides per page.",
            example = "100",
            defaultValue = "100",
            maximum = "100",
            nullable = true
    )
    @JsonProperty("size")
    private int size = 100;

    @Schema(
            name = "sortby",
            description = "Field by which results should be sorted (departure, created).",
            example = "departure",
            defaultValue = "departure",
            nullable = true
    )
    @JsonProperty("sortby")
    private String sortby = "departure";

    @Schema(
            name = "sortdirection",
            description = "Sorting direction: ASC for ascending, DESC for descending (CASE SENSITIVE).",
            example = "DESC",
            defaultValue = "DESC",
            nullable = true
    )
    @JsonProperty(value = "sortdirection")
    @Enumerated(EnumType.STRING)
    private Sort.Direction sortdirection = Sort.Direction.DESC;

    @Schema(
            name = "status",
            description = "Filter by status: PLANNED, ACTIVE, CANCELED, COMPLETED; (Default: All values)",
            example = "ACTIVE",
            nullable = true
    )
    @Enumerated(EnumType.STRING)
    private RideStatusType status = null;

    @Schema(
            name = "origincity",
            description = "Search by origincity",
            example = "Berlin",
            nullable = true
    )
    private String origincity = null;

    @Schema(
            name = "destinationcity",
            description = "Search by destinationcity",
            example = "Wolfsburg",
            nullable = true
    )
    private String destinationcity = null;

    @Schema(
            name = "username",
            description = "Search by username of creator",
            example = "sergej-stk",
            nullable = true
    )
    private String username = null;

    @Schema(
            name = "userid",
            description = "Search by id of creator",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9",
            nullable = true
    )
    private UUID userid = null;
}

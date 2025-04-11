package com.vahana.dtos.v1.rides;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vahana.dtos.v1.addresses.AddressDTO;
import com.vahana.dtos.v1.users.ShortUserDTO;
import com.vahana.utils.v1.rides.RideStatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Ride", description = "Represents a ride entity with detailed ride information.")
public final class RideDTO {
    @NotNull
    @Schema(
            name = "id",
            description = "A unique identifier for the ride in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("id")
    private UUID id;

    @NotNull
    @Schema(
            name = "departure",
            description = "The departure time of the ride in UTC format.",
            example = "2025-03-02T14:30:00Z"
    )
    @JsonProperty("departure")
    private Instant departure;

    @NotNull
    @Schema(
            name = "created",
            description = "The timestamp when the ride was created, in UTC format.",
            example = "2025-03-02T14:30:00Z"
    )
    @JsonProperty("created")
    private Instant created;

    @Schema(
            name = "driver",
            description = "The driver of the ride, represented as a shortened user entity.",
            implementation = ShortUserDTO.class
    )
    @JsonProperty("driver")
    private ShortUserDTO driver;

    @Schema(
            name = "origin",
            description = "The starting address of the ride.",
            implementation = AddressDTO.class
    )
    @JsonProperty("origin")
    private AddressDTO origin;

    @NotNull
    @Schema(
            name = "destination",
            description = "The destination address of the ride.",
            implementation = AddressDTO.class
    )
    @JsonProperty("destination")
    private AddressDTO destination;

    @Schema(
            name = "status",
            description = "The current status of the ride. Possible values: NONE, ACTIVE, PLANNED, CANCELED, COMPLETED.",
            example = "PLANNED"
    )
    @JsonProperty("status")
    @Enumerated(EnumType.STRING)
    private RideStatusType status;

    @Schema(
            name = "available_seats",
            description = "The number of available seats for the ride.",
            example = "4"
    )
    @JsonProperty("available_seats")
    private Integer availableSeats;
}

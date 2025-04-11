package com.vahana.dtos.v1.rides;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vahana.dtos.v1.addresses.UpdateAddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Create Ride", description = "Represents the data required to create a new ride.")
public final class CreateRideDTO {
    @Schema(
            name = "origin",
            description = "The starting address of the ride.",
            implementation = UpdateAddressDTO.class
    )
    @JsonProperty("origin")
    private UpdateAddressDTO origin;

    @Schema(
            name = "destination",
            description = "The destination address of the ride.",
            implementation = UpdateAddressDTO.class
    )
    @JsonProperty("destination")
    private UpdateAddressDTO destination;

    @Schema(
            name = "departure",
            description = "The planned departure time of the ride in UTC format.",
            example = "2025-03-02T14:30:00Z"
    )
    @JsonProperty("departure")
    private Instant departure;

    @Schema(
            name = "available_seats",
            description = "The number of available seats for the ride.",
            example = "4"
    )
    @JsonProperty("available_seats")
    private Integer availableSeats;
}

package com.vahana.dtos.v1.rides;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
public final class RideRegistrationDto {
    @NotNull
    @Schema(
            name = "id",
            description = "A unique identifier for the ride registration in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("id")
    private UUID id;

    @NotNull
    @Schema(
            name = "created",
            description = "The timestamp when the ride was created, in UTC format.",
            example = "2025-03-02T14:30:00Z"
    )
    @JsonProperty("created")
    private Instant created;

    @NotNull
    @Schema(
            name = "id",
            description = "A unique identifier for the ride in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("ride_id")
    private UUID rideID;

    @NotNull
    @Schema(
            name = "id",
            description = "A unique identifier for the user in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("user_id")
    private UUID userID;
}

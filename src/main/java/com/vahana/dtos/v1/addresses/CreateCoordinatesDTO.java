package com.vahana.dtos.v1.addresses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CreateCoordinates", description = "DTO for creating a new coordinates entity containing location details.")
public final class CreateCoordinatesDTO {
    @Schema(
            name = "latitude",
            description = "The geographic latitude coordinate of the location, using the WGS84 standard.",
            example = "48.137154"
    )
    @JsonProperty("latitude")
    @NotNull
    private Double latitude;

    @Schema(
            name = "longitude",
            description = "The geographic longitude coordinate of the location, using the WGS84 standard.",
            example = "11.576124"
    )
    @JsonProperty("longitude")
    @NotNull
    private Double longitude;
}
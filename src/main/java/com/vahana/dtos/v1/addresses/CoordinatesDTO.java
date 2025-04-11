package com.vahana.dtos.v1.addresses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Coordinates",
        title = "Geographical Coordinates",
        description = "Represents geographical coordinates (latitude and longitude) for a specific location, using the WGS84 coordinate system."
)
public final class CoordinatesDTO {
    @Schema(
            name = "latitude",
            title = "Latitude Coordinate",
            description = "The geographic latitude coordinate of the location, using the WGS84 standard. Latitude represents the north-south position on Earth.",
            example = "48.137154"
    )
    @JsonProperty("latitude")
    private Double latitude;

    @Schema(
            name = "longitude",
            title = "Longitude Coordinate",
            description = "The geographic longitude coordinate of the location, using the WGS84 standard. Longitude represents the east-west position on Earth.",
            example = "11.576124"
    )
    @JsonProperty("longitude")
    private Double longitude;
}

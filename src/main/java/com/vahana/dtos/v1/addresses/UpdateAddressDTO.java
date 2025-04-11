package com.vahana.dtos.v1.addresses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Update Address", description = "Represents the data required to update an address.")
public final class UpdateAddressDTO {
    @Schema(
            name = "street",
            description = "The name of the street.",
            example = "Main Street"
    )
    @JsonProperty("street")
    @Size(min = 1, max = 100)
    private String street;

    @Schema(
            name = "house_number",
            description = "The house or building number.",
            example = "42A"
    )
    @JsonProperty("house_number")
    @Size(min = 1, max = 100)
    private String houseNumber;

    @Schema(
            name = "postal_code",
            description = "The postal code of the address.",
            example = "12345"
    )
    @JsonProperty("postal_code")
    @Size(min = 1, max = 100)
    private String postalCode;

    @Schema(
            name = "city",
            description = "The city where the address is located.",
            example = "Berlin"
    )
    @JsonProperty("city")
    @Size(min = 1, max = 100)
    private String city;

    @Schema(
            name = "country",
            description = "The country where the address is located.",
            example = "Germany"
    )
    @JsonProperty("country")
    @Size(min = 1, max = 100)
    private String country;

    @Schema(
            name = "coordinates",
            description = "The geographical coordinates (latitude and longitude) provided for creating the address entry."
    )
    @JsonProperty("coordinates")
    private CreateCoordinatesDTO createCoordinates;
}

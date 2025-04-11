package com.vahana.dtos.v1.addresses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "Address",
        title = "Address Entity",
        description = "Represents an address entity containing detailed location information including street, house number, postal code, city, country, and optional coordinates."
)
public final class AddressDTO {
    @Schema(
            name = "id",
            title = "Address ID",
            description = "A unique identifier for the address, typically generated as a UUID. This ID ensures the uniqueness of the address entity.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("id")
    @NotNull
    private UUID id;

    @Schema(
            name = "street",
            title = "Street Name",
            description = "The name of the street where the address is located. This field represents the primary street name and is typically used for navigation.",
            example = "Main Street"
    )
    @JsonProperty("street")
    private String street;

    @Schema(
            name = "house_number",
            title = "House or Building Number",
            description = "The house or building number associated with the address. This field helps to pinpoint the exact location within the street.",
            example = "42A"
    )
    @JsonProperty("house_number")
    private String houseNumber;

    @Schema(
            name = "postal_code",
            title = "Postal Code",
            description = "The postal or ZIP code of the address. This field helps in categorizing the location within a specific postal region.",
            example = "12345"
    )
    @JsonProperty("postal_code")
    private String postalCode;

    @Schema(
            name = "city",
            title = "City Name",
            description = "The city where the address is located. This helps to identify the urban area associated with the address.",
            example = "Munich"
    )
    @JsonProperty("city")
    private String city;

    @Schema(
            name = "country",
            title = "Country Name",
            description = "The country where the address is located. This field identifies the nation associated with the address.",
            example = "Germany"
    )
    @JsonProperty("country")
    private String country;

    @Schema(
            name = "coordinates",
            title = "Geographical Coordinates",
            description = "The geographical coordinates (latitude and longitude) of the address. This is an optional field that represents the exact location on a map."
    )
    @JsonProperty("coordinates")
    private CoordinatesDTO coordinates;
}

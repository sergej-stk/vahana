package com.vahana.dtos.v1.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Shortened User", description = "Represents a shortened user entity with basic personal and contact information.")
public final class ShortUserDTO {
    @NotNull
    @Schema(
            name = "id",
            description = "A unique identifier for the user in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("id")
    private UUID id;

    @NotNull
    @NotEmpty(message = "User name is required")
    @JsonProperty("username")
    @Size(min = 1, max = 100)
    @Schema(name = "username", description = "The user's username.", example = "JohnDoe123")
    private String username;

    @Schema(
            name = "picture",
            description = "The user's profile picture id.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("picture_id")
    @NotBlank
    private UUID pictureID;
}

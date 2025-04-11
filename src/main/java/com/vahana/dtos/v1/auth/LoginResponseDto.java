package com.vahana.dtos.v1.auth;

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
@Schema(name = "Login Response", description = "Represents the response of a successful authentication.")
public class LoginResponseDto {

    @Schema(
            name = "token",
            description = "The authentication token used for authorized requests.",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKb2huRG9lQGV4YW1wbGVtYWlsLmNvbSIsImlhdCI6MTczOTM0MzgzOSwiZXhwIjoxNzM5MzQ3NDM5fQ.TkD-9OSm8EoD5AVTBnWF-ABuh7MQFxbz_sFrgd2XE1A"
    )
    @JsonProperty("token")
    @NotNull
    private String token;

    @Schema(
            name = "token_type",
            description = "The type of the token. Typically, it is a Bearer token.",
            example = "Bearer"
    )
    @JsonProperty("token_type")
    @NotNull
    private String tokenType = "Bearer";

    @Schema(
            name = "expires_in",
            description = "The duration in milliseconds until the token expires.",
            example = "3600000"
    )
    @JsonProperty("expires_in")
    @NotNull
    private long expiresIn;
}

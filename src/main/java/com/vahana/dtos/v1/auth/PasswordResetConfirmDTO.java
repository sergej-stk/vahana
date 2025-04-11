package com.vahana.dtos.v1.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Password Reset Confirm", description = "Confirm DTO for confirming a password reset process.")
public class PasswordResetConfirmDTO {
    @Schema(
            name = "token",
            description = "The password token used for password reset requests.",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiUEFTU1dPUkRfUkVTRVQiLCJzdWIiOiJTZXJnZWpTdGVpbnNpZWtAZ21haWwuY29tIiwiaWF0IjoxNzM5NzM1OTY5LCJleHAiOjE3Mzk3MzY4Njl9.81v1aBWtMcwIMkSDg-Z4ncjdiIMspuGMIaruc0qwloU"
    )
    @JsonProperty("token")
    @NotNull
    private String token;

    @Schema(
            name = "password",
            description = "The user's password, used for login.",
            example = "your-secret-password"
    )
    @NotNull
    @JsonProperty("password")
    @NotEmpty(message = "Password is required")
    @Size(min = 1, max = 255)
    private String password;
}

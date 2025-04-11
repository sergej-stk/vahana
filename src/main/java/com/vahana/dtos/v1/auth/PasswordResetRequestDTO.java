package com.vahana.dtos.v1.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(name = "Password Reset Request", description = "Request DTO for initiating a password reset process.")
public class PasswordResetRequestDTO {
    @NotNull
    @Schema(
            name = "email",
            description = "The user's email address, used for communication and login.",
            example = "JohnDoe@examplemail.com"
    )
    @Size(min = 1, max = 320)
    @JsonProperty("email")
    @NotEmpty(message = "E-Mail is required")
    @Email
    private String email;

}

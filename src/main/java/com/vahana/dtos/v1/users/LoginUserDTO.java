package com.vahana.dtos.v1.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Login User", description = "Represents a user login entity.")
public class LoginUserDTO {
    @Schema(
            name = "email",
            description = "The user's email address, used for communication and login.",
            example = "JohnDoe@examplemail.com"
    )
    @NotNull
    @JsonProperty("email")
    @Size(min = 1, max = 320)
    @NotEmpty(message = "E-Mail is required")
    @Email
    private String email;

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

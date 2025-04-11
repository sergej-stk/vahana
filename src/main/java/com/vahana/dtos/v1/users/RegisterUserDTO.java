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
@Schema(name = "Register User", description = "Represents a user registration entity.")
public class RegisterUserDTO {
    @Schema(name = "username", description = "The user's username.", example = "JohnDoe123")
    @NotNull
    @NotEmpty(message = "User name is required")
    @JsonProperty("username")
    @Size(min = 1, max = 100)
    private String username;

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

    @NotNull(message = "Privacy policy acceptance is required")
    @JsonProperty(value = "terms_accepted", required = true)
    private Boolean agreedToTerms;

    @Schema(
            name = "password",
            description = "The user's password, used for login.",
            example = "your-secret-password"
    )
    @JsonProperty("password")
    @NotNull
    @NotEmpty(message = "Password is required")
    @Size(min = 1, max = 255)
    private String password;

    @Schema(
            name = "lastname",
            description = "The user's last name.",
            example = "Doe"
    )
    @JsonProperty("lastname")
    @Size(min = 1, max = 100)
    private String lastname;

    @Schema(
            name = "firstname",
            description = "The user's first name.",
            example = "John"
    )
    @JsonProperty("firstname")
    @Size(min = 1, max = 100)
    private String firstname;
}

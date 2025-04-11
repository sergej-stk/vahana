package com.vahana.dtos.v1.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vahana.dtos.v1.addresses.AddressDTO;
import com.vahana.entities.v1.users.Role;
import com.vahana.utils.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Schema(name = "User", description = "Represents a user entity with basic personal and contact information.")
public class UserDTO {
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
            name = "role",
            description = "The user's role, used for permissions.",
            example = "USER"
    )
    @NotNull
    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Null
    @Schema(
            name = "lastname",
            description = "The user's last name.",
            example = "Doe"
    )
    @NotEmpty(message = "Name is required")
    @JsonProperty("lastname")
    @Size(min = 1, max = 100)
    private String lastname;

    @Null
    @Schema(
            name = "firstname",
            description = "The user's first name.",
            example = "John"
    )
    @NotEmpty(message = "Prename is required")
    @JsonProperty("firstname")
    @Size(min = 1, max = 100)
    private String firstname;

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

    @Schema(
            name = "phonenumber",
            nullable = true,
            description = "The user's phone number. Valid formats include:<ul>" +
                    "<li>+49 170 1234567</li>" +
                    "<li>+1-800-123 4567</li>" +
                    "<li>(+44) 20 7946 0958</li>" +
                    "<li>+33 1 70 12 34 56</li>" +
                    "<li>+49-40-123 4567</li>" +
                    "<li>(+1) 123 456 7890</li>" +
                    "<li>+41 (0) 44 567 8901</li></ul>",
            example = "+49 170 1234567"
    )
    @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX)
    @JsonProperty("phonenumber")
    @Size(min = 10, max = 20)
    private String phoneNumber;

    @Schema(
            name = "picture",
            description = "The user's profile picture id.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("picture_id")
    @NotBlank
    private UUID pictureID;

    @JsonProperty("address")
    private AddressDTO addressDTO;
}
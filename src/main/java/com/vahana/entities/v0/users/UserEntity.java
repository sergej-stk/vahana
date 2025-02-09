package com.vahana.entities.v0.users;

import com.vahana.utils.RegexConstants;
import io.swagger.v3.oas.annotations.OpenAPI31;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "user", description = "Represents a user entity with basic personal and contact information.")
public class UserEntity {
    @Schema(
            name = "id",
            description = "A unique identifier for the user in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    private UUID id;

    @Schema(
            name = "lastname",
            description = "The user's last name.",
            example = "Doe"
    )
    @Size(min = 1, max = 100)
    private String lastname;

    @Schema(
            name = "firstname",
            description = "The user's first name.",
            example = "John"
    )
    @Size(min = 1, max = 100)
    private String firstname;

    @Schema(
            name = "email",
            description = "The user's email address, used for communication and login.",
            example = "JohnDoe@examplemail.com"
    )
    @Size(min = 1, max = 320)
    @Email
    private String email;

    @Schema(
            name = "phonenumber",
            nullable = true,
            description = "The user's phone number. Valid formats include:\n" +
                    "+49 170 1234567\n" +
                    "+1-800-123 4567\n" +
                    "(+44) 20 7946 0958\n" +
                    "+33 1 70 12 34 56\n" +
                    "+49-40-123 4567\n" +
                    "(+1) 123 456 7890\n" +
                    "+41 (0) 44 567 8901",
            example = "+49 170 1234567"
    )
    @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX)
    @Size(min = 10, max = 20)
    private String phoneNumber;
}
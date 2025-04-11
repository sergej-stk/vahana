package com.vahana.dtos.v1.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vahana.dtos.v1.addresses.UpdateAddressDTO;
import com.vahana.utils.RegexConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Update User", description = "Represents a update user entity with basic personal and contact information.")
public final class UpdateUserDTO {
    @JsonProperty("username")
    @Size(min = 1, max = 100)
    @Schema(name = "username", description = "The user's username. Username must be unique.", example = "JohnDoe123")
    private String username;

    @Schema(
            name = "email",
            description = "The user's email address, used for communication and login.",
            example = "JohnDoe@examplemail.com"
    )
    @Size(min = 1, max = 320)
    @JsonProperty("email")
    @Email
    private String email;

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

    @JsonProperty("address")
    private UpdateAddressDTO addressDTO;
}

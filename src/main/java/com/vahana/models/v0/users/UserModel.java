package com.vahana.models.v0.users;

import com.vahana.utils.RegexConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
    public class UserModel {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "id", columnDefinition = "UUID", updatable = false, nullable = false)
        private UUID id;

        @Column(name = "created", nullable = false, updatable = false)
        private Instant created = Instant.now();

        @Column(name = "lastname", nullable = false)
        @NotEmpty(message = "Name is required")
        @Size(min = 1, max = 100, message = "Name must be between one and 100 characters long")
        private String lastname;

        @Column(name = "firstname", nullable = false)
        @NotEmpty(message = "Prename is required")
        @Size(min = 1, max = 100, message = "Prename must be between one and 100 characters long")
        private String firstname;

        @Column(name = "email", nullable = false, unique = true)
        @Email(message = "Must be a valid email address")
        private String email;

        @Column(name = "phone_number", unique = true)
        @Pattern(regexp = RegexConstants.PHONE_NUMBER_REGEX, message = "Must be a valid phonenumber")
        private String phoneNumber;
    }

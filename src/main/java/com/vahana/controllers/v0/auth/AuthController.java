package com.vahana.controllers.v0.auth;

import com.vahana.models.v0.users.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Endpoint", description = "Endpoints for user registration and authentication.")
@RestController
@RequestMapping("/vahana/v0/auth")
public class AuthController {

    @Operation(
            summary = "User Registration",
            description = "Creates a new user account based on the provided information.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful registration"),
                    @ApiResponse(responseCode = "400", description = "Invalid input data"),
                    @ApiResponse(responseCode = "409", description = "Username already taken")
            }
    )
    @Parameter()
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @RequestBody
            @Parameter(
                    description = "Test parameter for user registration",
                    allowEmptyValue = false,
                    example = "TestParam"
            ) String test) {
        return ResponseEntity.ok("User successfully registered");
    }

    @Operation(
            summary = "User Login",
            description = "Authenticates the user based on the provided login credentials.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful login"),
                    @ApiResponse(responseCode = "401", description = "Invalid login credentials"),
                    @ApiResponse(responseCode = "400", description = "Missing input data")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody UserModel user     ) {
        return ResponseEntity.ok("Successfully logged in");
    }
}

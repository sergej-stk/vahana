package com.vahana.controllers.v1.auth;

import com.vahana.controllers.v1.BaseController;
import com.vahana.dtos.v1.auth.LoginResponseDto;
import com.vahana.dtos.v1.auth.PasswordResetConfirmDTO;
import com.vahana.dtos.v1.auth.PasswordResetRequestDTO;
import com.vahana.dtos.v1.general.ErrorResponseDTO;
import com.vahana.dtos.v1.users.LoginUserDTO;
import com.vahana.dtos.v1.users.RegisterUserDTO;
import com.vahana.dtos.v1.users.UserDTO;
import com.vahana.services.v1.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Tag(name = "Authentication Endpoint", description = "Endpoints for user registration and authentication.")
@RequestMapping("/vahana/api/v1/auth")
@RequiredArgsConstructor
public final class AuthController extends BaseController {
    private final AuthService authService;

    @Operation(
            operationId = "registerUser",
            summary = "User Registration [NONE, USER, ADMIN]",
            description = "Creates a new user account based on the provided information.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody RegisterUserDTO registerUserDTO) {
        super.InitializeMDC();
        return authService.register(registerUserDTO);
    }

    @Operation(
            operationId = "loginUser",
            summary = "User Login [NONE, USER, ADMIN]",
            description = "Authenticates the user based on the provided login credentials.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginResponseDto.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> logIn(@RequestBody LoginUserDTO user) {
        super.InitializeMDC();
        return authService.login(user);
    }

    @Operation(
            operationId = "requestPasswordReset",
            summary = "Password Reset Request [NONE, USER, ADMIN]",
            description = "Sends a password reset email if the provided email is registered.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    @PostMapping("/password-reset/request")
    public ResponseEntity<ErrorResponseDTO> requestPasswordReset(@RequestBody PasswordResetRequestDTO passwordResetRequestDTO) {
        super.InitializeMDC();
        return authService.requestPasswordReset(passwordResetRequestDTO);
    }

    @Operation(
            operationId = "confirmPasswordReset",
            summary = "Password Reset Confirm [NONE, USER, ADMIN]",
            description = "Resets password of a user.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    @PostMapping("/password-reset/confirm")
    public ResponseEntity<UserDTO> confirmPasswordReset(@RequestBody PasswordResetConfirmDTO resetConfirmDTO) {
        super.InitializeMDC();
        return authService.confirmPasswordReset(resetConfirmDTO);
    }

    @PostMapping("/logout")
    @Operation(
            operationId = "logoutUser",
            summary = "User Logout [USER, ADMIN]",
            description = "User Logout.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<Void> logout() {
        super.InitializeMDC();
        return authService.logout();
    }
}

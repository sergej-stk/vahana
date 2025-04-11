package com.vahana.controllers.v1.users;

import com.vahana.controllers.v1.BaseController;
import com.vahana.dtos.v1.general.ErrorResponseDTO;
import com.vahana.dtos.v1.users.*;
import com.vahana.services.v1.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@CrossOrigin
@RestController
@Tag(name = "User Endpoint", description = "Endpoints for user data.")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("/vahana/api/v1/users")
public final class UserController extends BaseController {
    private final UserService userService;

    @GetMapping("/me")
    @Operation(
            operationId = "getAuthenticatedUser",
            summary = "Get authenticated user [USER, ADMIN]",
            description = "Retrieves the details of the currently authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
            }
    )
    public ResponseEntity<UserDTO> authenticatedUser() {
        super.InitializeMDC();
        return userService.getAuthenticatedUser();
    }

    @PatchMapping("/me")
    @Operation(
            operationId = "updateAuthenticatedUser",
            summary = "Change authenticated user information's [USER, ADMIN]",
            description = "Change the details of the currently authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
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
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
            }
    )
    public ResponseEntity<UserDTO> updateAuthenticatedUser(@RequestBody UpdateUserDTO updateUserDTO) {
        super.InitializeMDC();
        return userService.updateAuthenticatedUser(updateUserDTO);
    }

    @GetMapping("/{id}")
    @Operation(
            operationId = "getUserById",
            summary = "Get user by ID [USER, ADMIN]",
            description = "Retrieves the details of a user by their unique identifier.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShortUserDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    public ResponseEntity<ShortUserDTO> getUser(@PathVariable UUID id) {
        super.InitializeMDC();
        return userService.getUser(id);
    }

    @PatchMapping("/{id}")
    @Operation(
            operationId = "updateUserById",
            summary = "Change user information's [ADMIN]",
            description = "Change the details of an user by id.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
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
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
            }
    )
    public ResponseEntity<UserDTO> updateUser(@PathVariable UUID id, @RequestBody UpdateUserDTO updateUserDTO) {
        super.InitializeMDC();
        return userService.updateUser(id, updateUserDTO);
    }

    @GetMapping
    @Operation(
            operationId = "getAllUsers",
            summary = "Get all users [USER, ADMIN]",
            description = "Retrieves a paginated list of all users.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShortUserDTOList.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
            }
    )
    public ResponseEntity<ShortUserDTOList> getAllUser(@ParameterObject @Nullable FilterUserDTO filter) {
        super.InitializeMDC();
        return userService.getAllUser(filter);
    }

    @DeleteMapping("/me")
    @Operation(
            operationId = "deleteAuthenticatedUser",
            summary = "Delete  current user [USER, ADMIN]",
            description = "Deletes the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
            }
    )
    public ResponseEntity<Void> deleteUser() {
        super.InitializeMDC();
        return userService.deleteCurrentUser();
    }

    @DeleteMapping("/{id}")
    @Operation(
            operationId = "deleteUserById",
            summary = "Delete user by ID [ADMIN]",
            description = "Deletes a user by their unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        super.InitializeMDC();
        return userService.deleteUser(id);
    }
}

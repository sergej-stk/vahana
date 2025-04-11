package com.vahana.controllers.v1.rides;

import com.vahana.controllers.v1.BaseController;
import com.vahana.dtos.v1.general.ErrorResponseDTO;
import com.vahana.dtos.v1.rides.*;
import com.vahana.dtos.v1.users.FilterUserDTO;
import com.vahana.dtos.v1.users.ShortUserDTOList;
import com.vahana.services.v1.rides.RideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@Tag(name = "Rides Endpoint", description = "Endpoints for rides.")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("/vahana/api/v1/rides")
public final class RideController extends BaseController {
    private final RideService rideService;

    @PostMapping
    @Operation(
            operationId = "createRide",
            summary = "Create a Ride [USER, ADMIN]",
            description = "Creates a new ride based on the provided ride details.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Ride created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RideDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<RideDTO> createRide(@RequestBody CreateRideDTO createRideDTO) {
        super.InitializeMDC();
        return rideService.createRide(createRideDTO);
    }

    @GetMapping
    @Operation(
            operationId = "getAllRides",
            summary = "Get all Rides [USER, ADMIN]",
            description = "Retrieves a paginated list of all available rides.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of rides retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RideDTOList.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<RideDTOList> getAllRides(@ParameterObject @Valid @Nullable FilterRideDTO filter) {
        super.InitializeMDC();
        return rideService.getAllRides(filter);
    }

    @GetMapping("/me")
    @Operation(
            operationId = "getAuthenticatedUserRides",
            summary = "Get rides created by authenticated user [USER, ADMIN]",
            description = "Retrieves a paginated list of rides that belong to the currently authenticated user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of rides retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RideDTOList.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<RideDTOList> getAuthenticatedUserRides(@ParameterObject @Valid @Nullable FilterRideDTO filter) {
        super.InitializeMDC();
        return rideService.getAuthenticatedUserRides(filter);
    }

    @GetMapping("/{id}")
    @Operation(
            operationId = "getRideById",
            summary = "Get a ride by ID [USER, ADMIN]",
            description = "Retrieves a single ride based on the provided ride ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ride retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RideDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<RideDTO> getRide(@PathVariable UUID id) {
        super.InitializeMDC();
        return rideService.getRide(id);
    }

    @GetMapping("/{id}/participants")
    @Operation(
            operationId = "getAllParticipantsRideById",
            summary = "Get all Participants for a ride by ID [USER, ADMIN]",
            description = "Retrieves a User list based on the provided ride ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Ride participants retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ShortUserDTOList.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<ShortUserDTOList> getAllParticipantsRideById(@PathVariable UUID id, @ParameterObject @Nullable FilterUserDTO filter) {
        super.InitializeMDC();
        return rideService.getAllParticipantsRideById(id, filter);
    }

    @DeleteMapping("/me/{id}")
    @Operation(
            operationId = "deleteAuthenticatedUserRide",
            summary = "Delete a ride created by authenticated user [USER, ADMIN]",
            description = "Deletes a ride that belongs to the currently authenticated user based on the provided ride ID.",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ride not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<Void> deleteAAuthenticatedUserRide(@PathVariable UUID id) {
        super.InitializeMDC();
        return rideService.deleteAAuthenticatedUserRide(id);
    }

    @DeleteMapping("/{rideid}/participants/{userid}")
    @Operation(
            operationId = "removeParticipantOfRideById",
            summary = "Delete a participant of a ride by user id and ride id [USER, ADMIN]",
            description = "Delete a participant of a ride by user id and ride id.",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ride not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )

            }
    )
    public ResponseEntity<Void> removeParticipantOfRideById(@PathVariable UUID rideid, @PathVariable UUID userid) {
        super.InitializeMDC();
        return rideService.removeParticipantOfRideById(rideid, userid);
    }

    @DeleteMapping("/{id}")
    @Operation(
            operationId = "deleteRideById",
            summary = "Delete a ride by ID [ADMIN]",
            description = "Deletes a ride based on the provided ride ID. Only admins can perform this action.",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ride not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<Void> deleteRideByID(@PathVariable UUID id) {
        super.InitializeMDC();
        return rideService.deleteRideByID(id);
    }


    @PostMapping("/{id}/join")
    @Operation(
            operationId = "joinRide",
            summary = "Join a ride [USER, ADMIN]",
            description = "Allows an authenticated user to join a ride by providing the ride ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successfully joined the ride",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RideRegistrationDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ride not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Method Not Allowed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<RideRegistrationDto> joinRide(@PathVariable UUID id) {
        super.InitializeMDC();
        return rideService.joinRideByID(id);
    }

    @DeleteMapping("/{id}/leave")
    @Operation(
            operationId = "leaveRide",
            summary = "Leave a ride [USER ADMIN]",
            description = "Allows an authenticated user to leave a ride they have joined.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Successfully left the ride"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request parameters",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Ride not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "405",
                            description = "Method Not Allowed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class)
                            )
                    )
            }
    )
    public ResponseEntity<Void> leaveRide(@PathVariable UUID id) {
        super.InitializeMDC();
        return rideService.leaveRideById(id);
    }
}

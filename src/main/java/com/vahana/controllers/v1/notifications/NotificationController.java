package com.vahana.controllers.v1.notifications;

import com.vahana.controllers.v1.BaseController;
import com.vahana.dtos.v1.general.ErrorResponseDTO;
import com.vahana.dtos.v1.notifications.FilterNotificationDTO;
import com.vahana.dtos.v1.notifications.NotificationDTO;
import com.vahana.dtos.v1.notifications.NotificationDTOList;
import com.vahana.services.v1.notifications.NotificationService;
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
@Tag(name = "Notification Endpoint", description = "Endpoints for user notifications.")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/vahana/api/v1/notifications")
@RequiredArgsConstructor
public final class NotificationController extends BaseController {
    private final NotificationService notificationService;

    @GetMapping("/me")
    @Operation(
            operationId = "getAllNotifications",
            summary = "Get all notifications for the authenticated user [USER, ADMIN]",
            description = "Retrieves a list of notifications filtered by the provided parameters.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of notifications retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NotificationDTOList.class)
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
    public ResponseEntity<NotificationDTOList> getAllNotifications(@ParameterObject @Valid @Nullable FilterNotificationDTO filter) {
        super.InitializeMDC();
        return notificationService.getAllNotifications(filter);
    }

    @GetMapping("/{id}")
    @Operation(
            operationId = "getNotificationById",
            summary = "Retrieve a specific notification by ID [USER, ADMIN]",
            description = "Fetches a single notification by its unique identifier.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Notification retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NotificationDTO.class)
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
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable UUID id) {
        super.InitializeMDC();
        return notificationService.getNotificationById(id);
    }

    @PostMapping("/{id}/read")
    @Operation(
            operationId = "markNotificationAsRead",
            summary = "Mark a notification as read [USER, ADMIN]",
            description = "Updates the read status of a notification by its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Notification marked as read successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = NotificationDTO.class)
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
    public ResponseEntity<NotificationDTO> setReadStatusNotificationById(@PathVariable UUID id) {
        super.InitializeMDC();
        return notificationService.setReadStatusNotificationById(id);
    }
}

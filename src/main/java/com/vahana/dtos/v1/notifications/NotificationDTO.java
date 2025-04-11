package com.vahana.dtos.v1.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vahana.utils.v1.notifications.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Notification", description = "Represents a notification entity with detailed notification information.")
public final class NotificationDTO {
    @NotNull
    @Schema(
            name = "id",
            description = "A unique identifier for the notification in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("id")
    private UUID id;

    @NotNull
    @Schema(
            name = "user_id",
            description = "A unique identifier for the user in the system, typically generated as a UUID.",
            example = "d9b9c5e2-5d98-4e29-8887-cbe3b2b6a5a9"
    )
    @JsonProperty("user_id")
    private UUID userId;

    @NotNull
    @Schema(
            name = "type",
            description = "The notification's type, used for categorisation.",
            example = "RIDE"
    )
    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @NotNull
    @Schema(
            name = "message",
            description = "The message of the notification.",
            example = "Ride 232342 was canceled."
    )
    @JsonProperty("message")
    private String message;

    @NotNull
    @Schema(
            name = "read",
            description = "Read status.",
            example = "true"
    )
    @JsonProperty("read")
    private boolean read;
}

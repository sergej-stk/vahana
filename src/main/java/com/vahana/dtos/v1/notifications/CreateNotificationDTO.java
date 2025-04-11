package com.vahana.dtos.v1.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vahana.utils.v1.notifications.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "Create Notification", description = "Create notification options for retrieving notifications.")
public final class CreateNotificationDTO {
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

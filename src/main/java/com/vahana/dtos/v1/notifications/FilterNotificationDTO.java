package com.vahana.dtos.v1.notifications;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "Notification Filter", description = "Filter options for retrieving notifications.")
public final class FilterNotificationDTO {
    @Schema(
            name = "page",
            description = "Page number for pagination.",
            example = "0",
            defaultValue = "0",
            nullable = true
    )
    @JsonProperty("page")
    private int page = 0;

    @Schema(
            name = "size",
            description = "Number of rides per page.",
            example = "100",
            defaultValue = "100",
            maximum = "100",
            nullable = true
    )
    @JsonProperty("size")
    private int size = 100;

    @Schema(
            name = "read",
            description = "Read status of notifications.",
            example = "true",
            defaultValue = "false",
            nullable = true
    )
    @JsonProperty("read")
    private boolean read = false;
}

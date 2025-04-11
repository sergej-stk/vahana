package com.vahana.dtos.v1.general;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        title = "Error Response",
        $comment = "Standard API Error Response",
        description = "Represents a standardized error response containing essential details such as the timestamp, HTTP status code, error message, and the API endpoint that generated the response."
)
public final class ErrorResponseDTO {
    @Schema(
            name = "timestamp",
            title = "Timestamp",
            description = "The time at which the error response was generated.",
            example = "2025-04-03T10:00:00Z"
    )
    private OffsetDateTime timestamp;

    @Schema(
            name = "status",
            title = "HTTP Status Code",
            description = "The HTTP status code indicating the outcome of the API request. Typically reflects an error when non-successful.",
            example = "400"
    )
    private int status;

    @Schema(
            name = "message",
            title = "Error Message",
            description = "A brief description summarizing the error that occurred during the API request.",
            example = "An error occurred while processing the request."
    )
    private String message;

    @Schema(
            name = "path",
            title = "API Path",
            description = "The API endpoint that produced this error response.",
            example = "/api/resource"
    )
    private String path;
}

package com.vahana.dtos.v1.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(name = "User Filter", description = "Filter options for retrieving users.")
public final class FilterUserDTO {
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
}

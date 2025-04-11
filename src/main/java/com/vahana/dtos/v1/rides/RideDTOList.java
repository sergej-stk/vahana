package com.vahana.dtos.v1.rides;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Schema(name = "Ride List", description = "Represents a paginated list of RideDTO objects.")
public final class RideDTOList extends PageImpl<RideDTO> {
    public RideDTOList(List<RideDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

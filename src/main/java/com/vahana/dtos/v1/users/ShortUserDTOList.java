package com.vahana.dtos.v1.users;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Schema(name = "Shortened User List", description = "Represents a shortened user entity list with basic personal and contact information.")
public final class ShortUserDTOList extends PageImpl<ShortUserDTO> {
    public ShortUserDTOList(List<ShortUserDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

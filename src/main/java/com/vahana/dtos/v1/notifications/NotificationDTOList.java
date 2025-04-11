package com.vahana.dtos.v1.notifications;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Schema(name = "Notification List", description = "Represents a notification entity list with notification information.")
public class NotificationDTOList extends PageImpl<NotificationDTO> {
    public NotificationDTOList(List<NotificationDTO> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }
}

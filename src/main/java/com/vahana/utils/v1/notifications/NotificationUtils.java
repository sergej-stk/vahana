package com.vahana.utils.v1.notifications;

import com.vahana.dtos.v1.notifications.NotificationDTO;
import com.vahana.entities.v1.notifications.NotificationEntity;

public final class NotificationUtils {
    public static NotificationDTO convertNotificationEntityToNotificationDTO(NotificationEntity notificationEntity) {
        return new NotificationDTO()
                .setId(notificationEntity.getId())
                .setUserId(notificationEntity.getUser().getId())
                .setType(notificationEntity.getType())
                .setMessage(notificationEntity.getMessage())
                .setRead(notificationEntity.isRead());
    }
}

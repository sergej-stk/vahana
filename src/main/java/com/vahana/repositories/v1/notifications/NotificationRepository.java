package com.vahana.repositories.v1.notifications;

import com.vahana.entities.v1.notifications.NotificationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface NotificationRepository extends CrudRepository<NotificationEntity, UUID> {
    Page<NotificationEntity> findAllByUser_IdAndRead(UUID userId, boolean read, Pageable pageable);
    @Override
    <S extends NotificationEntity> S save(S entity);
    Integer countByUser_IdAndRead(UUID userId, boolean read);
}

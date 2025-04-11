package com.vahana.services.v1.notifications;

import com.vahana.dtos.v1.notifications.CreateNotificationDTO;
import com.vahana.dtos.v1.notifications.FilterNotificationDTO;
import com.vahana.dtos.v1.notifications.NotificationDTO;
import com.vahana.dtos.v1.notifications.NotificationDTOList;
import com.vahana.entities.v1.notifications.NotificationEntity;
import com.vahana.entities.v1.users.Role;
import com.vahana.repositories.v1.notifications.NotificationRepository;
import com.vahana.repositories.v1.users.UserRepository;
import com.vahana.security.exceptions.BadRequestException;
import com.vahana.security.exceptions.InternalServerErrorException;
import com.vahana.security.exceptions.PictureNotFoundException;
import com.vahana.security.exceptions.UserPermissionException;
import com.vahana.services.v1.auth.PermissionService;
import com.vahana.services.v1.users.UserService;
import com.vahana.utils.v1.notifications.NotificationUtils;
import io.micrometer.common.lang.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public final class NotificationService {
    private final UserService userService;
    private final PermissionService permissionService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public ResponseEntity<NotificationDTOList> getAllNotifications(@Nullable FilterNotificationDTO filter) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("Fetching notifications for user: {}", user.getId());
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        if (filter == null) {
            log.debug("Filter is null, using default values.");
            filter = new FilterNotificationDTO();
        }
        validateFilter(filter);

        log.debug("Applying filter: read={}, page={}, size={}", filter.isRead(), filter.getPage(), filter.getSize());
        var total = notificationRepository.countByUser_IdAndRead(user.getId(), filter.isRead());
        var notifications = notificationRepository.findAllByUser_IdAndRead(
                user.getId(),
                filter.isRead(),
                PageRequest.of(filter.getPage(), filter.getSize())
        );
        var result = notifications.map(NotificationUtils::convertNotificationEntityToNotificationDTO).toList();

        return ResponseEntity.ok(
                new NotificationDTOList(
                        result,
                        PageRequest.of(
                                filter.getPage(),
                                filter.getSize(),
                                Sort.by(Sort.Direction.DESC, "created")),
                        total
                )
        );
    }

    private void validateFilter(@Nullable @Valid FilterNotificationDTO filter) {
        if (filter == null)
            return;

        if(filter.getSize() < 1 || filter.getSize() > 100) {
            log.warn("Invalid filter size: {}. Must be between 1 and 100.", filter.getSize());
            throw new BadRequestException("Filter size '" + filter.getSize() + "' is invalid (Range 1..100).");
        }

        if(filter.getPage() < 0) {
            log.warn("Invalid filter page: {}. Must be 0 or greater.", filter.getPage());
            throw new BadRequestException("Filter page '" + filter.getPage() + "' is invalid (Range 0..n).");
        }
    }

    public void createNotification(UUID userId, CreateNotificationDTO createNotificationDTO) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("Creating notification for user: {} by user: {}", userId, user.getId());
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var result = userRepository.findById(userId).orElseThrow(() -> new InternalServerErrorException("User '" + userId + "' not found."));

        var notification = new NotificationEntity()
                .setUser(result)
                .setType(createNotificationDTO.getType())
                .setRead(createNotificationDTO.isRead())
                .setMessage(createNotificationDTO.getMessage());
        notificationRepository.save(notification);
        log.info("Notification created successfully for user: {}", userId);
    }

    public ResponseEntity<NotificationDTO> setReadStatusNotificationById(UUID id) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("Set notification by id {} for user: {} Status READ=TRUE", id, user.getId());
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var result = notificationRepository.findById(id).orElseThrow(PictureNotFoundException::new);
        if (!result.getUser().getId().equals(user.getId()))
            throw new UserPermissionException();

        log.info("Set notification by id {} true.", result.getId());
        result.setRead(true);
        notificationRepository.save(result);

        return ResponseEntity.status(HttpStatus.CREATED).body(NotificationUtils.convertNotificationEntityToNotificationDTO(result));

    }

    public ResponseEntity<NotificationDTO> getNotificationById(UUID id) {
        var user = userService.getAuthenticatedUserEntity();
        log.info("Fetching notification by id {} for user: {}", id, user.getId());
        permissionService.checkUserPermission(user, List.of(Role.USER, Role.ADMIN));

        var result = notificationRepository.findById(id).orElseThrow(PictureNotFoundException::new);
        if (!result.getUser().getId().equals(user.getId()))
            throw new UserPermissionException();

        log.info("Fetched successfully notification by id {}.", result.getId());
        return ResponseEntity.ok(NotificationUtils.convertNotificationEntityToNotificationDTO(result));
    }
}

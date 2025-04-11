package com.vahana.services.v1.auth;

import com.vahana.entities.v1.users.Role;
import com.vahana.entities.v1.users.UserEntity;
import com.vahana.security.exceptions.UserPermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public final class PermissionService {
    public static String USER_NO_PERMISSION = "do not have permission to access this resource";

    public void checkUserPermission(UserEntity user, List<Role> allowedRoles) {
        if (!allowedRoles.contains(user.getRole())) {
            log.info("{} {}", user.getUsername(), USER_NO_PERMISSION);
            throw new UserPermissionException();
        }
    }
}

package com.vahana.services.v1.users;

import com.vahana.entities.v1.users.UserAuditEntity;
import com.vahana.entities.v1.users.UserEntity;
import com.vahana.repositories.v1.users.UserAuditRepository;
import com.vahana.utils.v1.users.ActionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public final class UserAuditService {
    private final UserAuditRepository userAuditRepository;

    public void logUserAction(UserEntity user, ActionType action, String changes) {
        UserAuditEntity audit = new UserAuditEntity()
                .setUser(user)
                .setAction(action)
                .setChanges(changes);
        userAuditRepository.save(audit);
    }
}

package com.vahana.configurations.logging;

import com.vahana.repositories.v1.users.UserAuditRepository;
import com.vahana.utils.v1.MDCUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public final class UserAuditCleanupTask {
    private final UserAuditRepository repository;

    //every hour
    @Scheduled(fixedDelay = 3600000)
    public void deleteOldEntries() {
        MDCUtils.configureMDCVar(CustomMDCTypes.TARGET, TargetTypes.SYSTEM);
        var cutoff = LocalDateTime.now().minusDays(30);
        log.info("Starting scheduled UserAudit cleanup: Deleting entries older than {}", cutoff);
        repository.deleteEntriesOlderThan(cutoff);
    }
}

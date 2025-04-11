package com.vahana.configurations.tasks;

import com.vahana.configurations.logging.CustomMDCTypes;
import com.vahana.configurations.logging.TargetTypes;
import com.vahana.repositories.v1.rides.RideRepository;
import com.vahana.utils.v1.MDCUtils;
import com.vahana.utils.v1.rides.RideStatusType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public final class RideStatusCleanupTask {
    private final RideRepository repository;

    //every 5 min
    @Scheduled(fixedDelay = 300000)
    public void changeStatusPlannedToActive() {
        MDCUtils.configureMDCVar(CustomMDCTypes.TARGET, TargetTypes.SYSTEM);
        log.info("Start Task: changeStatusPlannedToActive");
        var cutoff = Instant.now();

        var values = repository.findByDepartureGreaterThanEqualAndStatus(
                cutoff, RideStatusType.PLANNED
        );

        if (values.isEmpty()) {
            log.info("No rides found.");
            return;
        }

        log.info("Found {} rides", values.size());
        values.forEach(val -> val.setStatus(RideStatusType.ACTIVE));
        repository.saveAll(values);
    }

    // every 5 min
    @Scheduled(fixedDelay = 300000)
    public void changeStatusActiveToCompleted() {
        MDCUtils.configureMDCVar(CustomMDCTypes.TARGET, TargetTypes.SYSTEM);
        log.info("Start Task: changeStatusActiveToCompleted");
        var cutoff = Instant.now().minus(1, ChronoUnit.DAYS);

        var values = repository.findByDepartureLessThanAndStatus(
                cutoff, RideStatusType.ACTIVE
        );

        if (values.isEmpty()) {
            log.info("No rides found.");
            return;
        }

        log.info("Found {} rides", values.size());
        values.forEach(val -> val.setStatus(RideStatusType.COMPLETED));
        repository.saveAll(values);
    }
}

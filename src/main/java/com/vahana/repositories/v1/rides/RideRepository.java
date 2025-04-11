package com.vahana.repositories.v1.rides;

import com.vahana.entities.v1.rides.RideEntity;
import com.vahana.utils.v1.rides.RideStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface RideRepository extends CrudRepository<RideEntity, UUID>, JpaSpecificationExecutor<RideEntity> {
    Page<RideEntity> findAll(Specification<RideEntity> specification, Pageable pageable);
    long count(Specification<RideEntity> specification);

    @Override
    <S extends RideEntity> S save(S entity);
    long countByDriver_idAndStatus(UUID driverId, RideStatusType status);
    Page<RideEntity> findByDriver_idAndStatus(UUID driverId, RideStatusType status, Pageable pageable);
    List<RideEntity> findByDepartureGreaterThanEqualAndStatus(Instant departureIsGreaterThan, RideStatusType status);
    List<RideEntity> findByDepartureLessThanAndStatus(Instant departureIsLessThan, RideStatusType status);
}

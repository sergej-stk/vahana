package com.vahana.repositories.v1.rides;

import com.vahana.entities.v1.rides.RideEntity;
import com.vahana.entities.v1.rides.UserRideEntity;
import com.vahana.entities.v1.users.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface UserRideRepository extends CrudRepository<UserRideEntity, UUID> {
    @Override
    <S extends UserRideEntity> S save(S entity);
    boolean existsByUserAndRide(UserEntity user, RideEntity ride);
    boolean existsByUser_IdAndRide_Id(UUID userId, UUID rideId);

    List<UserRideEntity> removeByUser_IdAndRide_Id(UUID userId, UUID rideId);
    
    long countByRide_Id(UUID rideId);

    @Transactional
    @Modifying
    void deleteByUserAndRide(UserEntity user, RideEntity ride);

    List<UserRideEntity> findAllByRide_id(UUID rideId);
}

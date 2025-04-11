package com.vahana.repositories.v1.users;

import com.vahana.entities.v1.users.UserAuditEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserAuditRepository extends CrudRepository<UserAuditEntity, UUID> {
    @Override
    <S extends UserAuditEntity> S save(S entity);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_audits WHERE timestamp < :cutoff", nativeQuery = true)
    void deleteEntriesOlderThan(@Param("cutoff") LocalDateTime cutoff);
}

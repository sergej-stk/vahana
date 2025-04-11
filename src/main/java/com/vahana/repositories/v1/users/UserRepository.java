package com.vahana.repositories.v1.users;

import com.vahana.entities.v1.users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {
    Optional<UserEntity> findById(UUID id);
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity> findAll(Pageable pageable);

    @Override
    <S extends UserEntity> S save(S entity);
}

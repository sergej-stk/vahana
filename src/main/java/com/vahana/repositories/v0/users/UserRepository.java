package com.vahana.repositories.v0.users;

import com.vahana.models.v0.users.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserModel, UUID> {
    Optional<UserModel> findById(UUID id);
}

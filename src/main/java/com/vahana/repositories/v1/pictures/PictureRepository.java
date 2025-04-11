package com.vahana.repositories.v1.pictures;

import com.vahana.entities.v1.pictures.PictureEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface PictureRepository extends CrudRepository<PictureEntity, UUID> {
    Optional<PictureEntity> findById(UUID uuid);
}

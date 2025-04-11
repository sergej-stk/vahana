package com.vahana.repositories.v1.addresses;

import com.vahana.entities.v1.addresses.AddressEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<AddressEntity, UUID> {
    @Override
    <S extends AddressEntity> S save(S entity);
}

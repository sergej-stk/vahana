package com.vahana.services.v0.users;

import com.vahana.entities.v0.users.CreateUserEntity;
import com.vahana.entities.v0.users.UserEntity;
import com.vahana.models.v0.users.UserModel;
import com.vahana.repositories.v0.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserEntity> getUser(UUID id)
    {
        var user = userRepository.findById(id);

        return user.map(userModel -> ResponseEntity.ok(
                        convertUserModelToUserEntity(userModel)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserEntity> addUser(CreateUserEntity createUserEntity)
    {

        //userRepository.save();
        return ResponseEntity.ok(new UserEntity());
    }

    private static UserEntity convertUserModelToUserEntity(UserModel model) {
        var result = new UserEntity();
        result.setId(model.getId());
        result.setLastname(model.getLastname());
        result.setFirstname(model.getFirstname());
        result.setEmail(model.getEmail());
        result.setPhoneNumber(model.getPhoneNumber());
        return result;
    }
}

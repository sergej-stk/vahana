package com.vahana.controllers.v0.users;

import com.vahana.entities.v0.users.CreateUserEntity;
import com.vahana.entities.v0.users.UserEntity;
import com.vahana.services.v0.users.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/vahana/v0/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable UUID id) {
        return userService.getUser(id);
    }

    @PostMapping()
    public ResponseEntity<UserEntity> createUser(@RequestBody CreateUserEntity user) {
        return userService.addUser(user);
    }
}

package com.vahana.controllers.v0.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v0/auth")
public class AuthController {
    @GetMapping()
    public ResponseEntity<String> getLogin() {
        return ResponseEntity.ok("Hello World");
    }
}

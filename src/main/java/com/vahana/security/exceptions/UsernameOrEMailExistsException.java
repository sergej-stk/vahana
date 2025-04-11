package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class UsernameOrEMailExistsException extends ResponseStatusException {
    public UsernameOrEMailExistsException() {
        super(HttpStatus.CONFLICT, "Email or Username already exists.");
    }
}

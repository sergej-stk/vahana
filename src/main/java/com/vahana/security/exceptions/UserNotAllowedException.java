package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class UserNotAllowedException extends ResponseStatusException {
    public UserNotAllowedException(String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }
}

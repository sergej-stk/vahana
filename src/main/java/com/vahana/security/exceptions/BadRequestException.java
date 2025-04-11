package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class BadRequestException extends ResponseStatusException {
    public BadRequestException() {
        super(HttpStatus.BAD_REQUEST, "Bad Request.");
    }

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, "Bad Request: " + message);
    }
}

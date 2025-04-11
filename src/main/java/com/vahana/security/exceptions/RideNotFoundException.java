package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class RideNotFoundException extends ResponseStatusException {
    public RideNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Ride not found");
    }
}

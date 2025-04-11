package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public final class DriverNotAllowedToJoinException extends ResponseStatusException {
    public DriverNotAllowedToJoinException(UUID rideId, String reason) {
        super(HttpStatus.FORBIDDEN, "Driver not allowed to join ride " + rideId + ": " + reason);
    }
}
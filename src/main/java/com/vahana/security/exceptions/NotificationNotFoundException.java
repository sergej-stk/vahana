package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class NotificationNotFoundException extends ResponseStatusException {
    public NotificationNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Notification not found");
    }
}

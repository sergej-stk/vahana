package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class UserPermissionException extends ResponseStatusException {
    public UserPermissionException() {
        super(HttpStatus.FORBIDDEN, "User do not have permission to access this resource");
    }
}

package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class NoChangesException extends ResponseStatusException {
    public NoChangesException() {
        super(HttpStatus.BAD_REQUEST, "No changes to be made");
    }
}

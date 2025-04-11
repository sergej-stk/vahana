package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class PictureNotFoundException extends ResponseStatusException {
    public PictureNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Picture not found");
    }
}

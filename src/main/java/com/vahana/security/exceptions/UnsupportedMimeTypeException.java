package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnsupportedMimeTypeException extends ResponseStatusException {
    public UnsupportedMimeTypeException() {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported image format. Allowed formats: JPEG, PNG, GIF, WEBP, BMP, SVG");
    }
}

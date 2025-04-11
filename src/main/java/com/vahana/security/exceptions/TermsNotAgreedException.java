package com.vahana.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class TermsNotAgreedException extends ResponseStatusException {
    public TermsNotAgreedException() {
        super(HttpStatus.FORBIDDEN, "Terms and conditions must be agreed to before proceeding.");
    }
}

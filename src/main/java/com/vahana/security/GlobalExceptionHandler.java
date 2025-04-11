package com.vahana.security;

import com.vahana.dtos.v1.general.ErrorResponseDTO;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.security.SignatureException;
import java.time.OffsetDateTime;

@Hidden
@ControllerAdvice
public final class GlobalExceptionHandler {
    @Value("${app.debug:false}")
    private boolean isDebug;

    @ExceptionHandler({
            SignatureException.class,
            UsernameNotFoundException.class,
            JwtException.class
    })
    public ResponseEntity<Object> handleJwtExceptions(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponseDTO()
                        .setTimestamp(OffsetDateTime.now())
                        .setStatus(HttpStatus.UNAUTHORIZED.value())
                        .setMessage(!isDebug ? "Invalid token" : ex.getMessage())
                        .setPath(requestUri));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO()
                        .setTimestamp(OffsetDateTime.now())
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setMessage(!isDebug ? "Internal Server Error" : ex.getMessage())
                        .setPath(requestUri));
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO()
                        .setTimestamp(OffsetDateTime.now())
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setMessage(!isDebug ? "Internal Server Error" : ex.getMessage())
                        .setPath(requestUri));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValidException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO()
                        .setTimestamp(OffsetDateTime.now())
                        .setStatus(HttpStatus.BAD_REQUEST.value())
                        .setMessage(!isDebug ? "Bad Request Error" : ex.getMessage())
                        .setPath(requestUri));
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO()
                        .setTimestamp(OffsetDateTime.now())
                        .setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .setMessage(!isDebug ? "Internal Server Error" : ex.getMessage())
                        .setPath(requestUri));
    }

    @ExceptionHandler({HttpMessageConversionException.class})
    public ResponseEntity<Object> handleHttpMessageConversionException(Exception ex, WebRequest request) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO()
                        .setTimestamp(OffsetDateTime.now())
                        .setStatus(HttpStatus.BAD_REQUEST.value())
                        .setMessage(!isDebug ? "Bad Request Error" : ex.getMessage())
                        .setPath(requestUri));
    }
}
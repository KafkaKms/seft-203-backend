package com.kms.seft203.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;

@SuppressWarnings("unused")
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private Object toJson(Exception exception, WebRequest request) {
        var body = new HashMap<>();
        body.put("errorMessage", exception.getMessage());
        body.put("path", request.getContextPath());
        body.put("timestamp", LocalDateTime.now());

        return body;
    }
    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException exception, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(toJson(exception, request));
    }
}

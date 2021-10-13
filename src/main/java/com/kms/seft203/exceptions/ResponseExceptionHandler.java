package com.kms.seft203.exceptions;

import io.swagger.v3.core.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@SuppressWarnings("unused")
@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    private Object toJson(Exception exception, WebRequest webRequest, HttpServletRequest httpServletRequest) {
        var data = new HashMap<>();
        data.put("errorMessage", exception.getMessage());
        data.put("method", httpServletRequest.getMethod());
        data.put("path", httpServletRequest.getRequestURI());
        data.put("parameters", webRequest.getParameterMap());
        try {
            data.put(
                    "body",
                    Json.mapper().readValue(httpServletRequest.getReader(), Object.class)
            );
        } catch (IOException e) {
            data.put("body", null);
        }

        data.put("timestamp", LocalDateTime.now());
        return data;
    }

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException exception, WebRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(toJson(exception, request, httpServletRequest));
    }
}

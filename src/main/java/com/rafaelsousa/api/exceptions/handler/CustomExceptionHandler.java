package com.rafaelsousa.api.exceptions.handler;

import com.rafaelsousa.api.exceptions.ExceptionResponse;
import com.rafaelsousa.api.exceptions.InvalidParameterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getObjectResponseEntity(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getObjectResponseEntity(ex, headers, status, request);
    }

    @ExceptionHandler(InvalidParameterException.class)
    protected ResponseEntity<Object> invalidParameterException(Exception ex, WebRequest request) {
        ExceptionResponse response = ExceptionResponse
                .builder()
                .timestamp(LocalDateTime.now().format(ofPattern("yyyy-MM-dd HH:mm:ss")))
                .code(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .details("Invalid parameter")
                .errors(Collections.singletonList(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, response.getStatus());
    }

    private ResponseEntity<Object> getObjectResponseEntity(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String errorText = String.format("%s : %s", error.getField(), error.getDefaultMessage());

            if (!errors.contains(errorText)) {
                errors.add(errorText);
            }
        });

        ex.getBindingResult().getGlobalErrors().forEach(error -> {
            String errorText = String.format("%s : %s", error.getObjectName(), error.getDefaultMessage());

            if (!errors.contains(errorText)) {
                errors.add(errorText);
            }
        });

        ExceptionResponse response = ExceptionResponse
                .builder()
                .timestamp(LocalDateTime.now().format(ofPattern("yyyy-MM-dd HH:mm:ss")))
                .code(BAD_REQUEST.value())
                .status(BAD_REQUEST)
                .details("DTO validation errors")
                .errors(errors)
                .build();

        return handleExceptionInternal(ex, response, headers, status, request);
    }

}

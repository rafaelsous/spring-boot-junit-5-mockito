package com.rafaelsousa.api.exceptions;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Builder
public class ExceptionResponse {

    private final String timestamp;
    private final int code;
    private final HttpStatus status;
    private final String details;
    private final List<String> errors;

}

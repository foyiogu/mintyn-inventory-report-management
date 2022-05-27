package org.mintyn.app.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
@ResponseStatus
public class ApiExceptionHandler {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {ApiBadRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiBadRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(),
                false, badRequest, LocalDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, badRequest);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {ApiResourceNotFoundException.class})
    public ResponseEntity<Object> handleApiResourceNotFoundException(ApiResourceNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(e.getMessage(),
                false, notFound, LocalDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, notFound);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {ApiRequestUnauthorizedException.class})
    public ResponseEntity<Object> handleApiRequestUnauthorizedException(ApiResourceNotFoundException e) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(e.getMessage(),
                false, unauthorized, LocalDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException, unauthorized);
    }



}

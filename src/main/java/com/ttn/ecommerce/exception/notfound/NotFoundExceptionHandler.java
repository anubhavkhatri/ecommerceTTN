package com.ttn.ecommerce.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class NotFoundExceptionHandler {
    @ExceptionHandler(value = {NotFoundRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(NotFoundRequestException e) {
        //1. Create Payload
        NotFoundException apiException = new NotFoundException(e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("Z")));
        //2. Return ResponseEntity
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

}

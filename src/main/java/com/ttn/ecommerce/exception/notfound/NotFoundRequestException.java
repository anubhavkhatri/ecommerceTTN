package com.ttn.ecommerce.exception.notfound;

import org.springframework.http.HttpStatus;

public class NotFoundRequestException extends RuntimeException{

    public NotFoundRequestException(String message, HttpStatus httpStatus) {
        super(message);
    }
}

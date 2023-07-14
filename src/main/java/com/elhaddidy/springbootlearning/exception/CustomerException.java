package com.elhaddidy.springbootlearning.exception;

import org.springframework.http.HttpStatus;

public class CustomerException {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    public CustomerException(String message, Throwable throwable, HttpStatus httpStatus) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

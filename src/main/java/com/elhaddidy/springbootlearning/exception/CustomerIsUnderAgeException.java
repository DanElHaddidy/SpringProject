package com.elhaddidy.springbootlearning.exception;

public class CustomerIsUnderAgeException extends RuntimeException {


    public CustomerIsUnderAgeException(String message) {
        super(message);
    }

    public CustomerIsUnderAgeException(String message, Throwable cause) {
        super(message, cause);
    }
}

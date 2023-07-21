package com.elhaddidy.springbootlearning.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class CustomerExceptionsHandler {

    @ExceptionHandler(value = {CustomerNotFoundException.class})
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception){

        CustomerException customerException = new CustomerException(
                exception.getMessage(),
                exception.getCause(),
                HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(customerException,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {CustomerIsUnderAgeException.class})
    public ResponseEntity<Object> handleCustomerIsUnderAgeException(CustomerIsUnderAgeException exception){

        CustomerException customerException = new CustomerException(
                exception.getMessage(),
                exception.getCause(),
                HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(customerException,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DuplicateResourceException.class})
    public ResponseEntity<Object> handleDuplicateResourceException(DuplicateResourceException exception){

        CustomerException customerException = new CustomerException(
                exception.getMessage(),
                exception.getCause(),
                HttpStatus.CONFLICT);

        return new ResponseEntity<>(customerException,HttpStatus.CONFLICT);
    }

}

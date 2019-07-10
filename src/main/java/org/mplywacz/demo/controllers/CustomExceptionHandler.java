package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 09.07.2019
*/

import org.mplywacz.demo.exceptions.IncorrectLocationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = IncorrectLocationException.class)
    ResponseEntity<Object> handleIncorrectLocation(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //handle error msg's for @Valid
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleDtoValidation(MethodArgumentNotValidException ex, WebRequest request) {
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(er -> {
            String fieldName = ((FieldError) er).getField();
            String errorMessage = er.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    ResponseEntity<Object> handleIncorrectDateInput(RuntimeException ex) {
        return new ResponseEntity<>("Given date is wrong, couldn't parse. Appropriate format: yyyy-mm-dd",
                HttpStatus.UNPROCESSABLE_ENTITY);

    }

}

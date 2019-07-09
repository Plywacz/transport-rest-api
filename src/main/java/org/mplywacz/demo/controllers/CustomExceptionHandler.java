package org.mplywacz.demo.controllers;
/*
Author: BeGieU
Date: 09.07.2019
*/

import org.mplywacz.demo.exceptions.IncorrectLocationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = IncorrectLocationException.class)
    ResponseEntity<Object> handleIncorrectLocation(RuntimeException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

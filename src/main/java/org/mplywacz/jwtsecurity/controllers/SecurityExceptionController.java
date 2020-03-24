package org.mplywacz.jwtsecurity.controllers;
/*
Author: BeGieU
Date: 27.11.2019
*/

import org.mplywacz.transitapi.exceptions.EntityAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin(origins = "*")
@ControllerAdvice
public class SecurityExceptionController {
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseBody ResponseEntity<String> handleWrongJson(Exception ex, HttpServletResponse response)
            throws IOException {
        ex.printStackTrace();
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {EntityAlreadyExistException.class})
    @ResponseBody ResponseEntity<String> handleUsernameAlreadyTaken(RuntimeException ex, HttpServletResponse response)
            throws IOException {
        ex.printStackTrace();
        response.sendError(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.CONFLICT);
    }

}

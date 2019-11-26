package org.mplywacz.transitapi.controllers;
/*
Author: BeGieU
Date: 09.07.2019
*/

import com.fasterxml.jackson.core.JsonParseException;
import org.mplywacz.transitapi.exceptions.IllegalDateInputException;
import org.mplywacz.transitapi.exceptions.IncorrectLocationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.NoSuchElementException;

//todo fix unsuitable HttpsStatus in response entity

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = IncorrectLocationException.class)
    ResponseEntity<Object> handleIncorrectLocation(RuntimeException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //handle error msg's for @Valid
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleDtoValidation(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(er -> {
            String fieldName = ((FieldError) er).getField();
            String errorMessage = er.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalDateInputException.class,
            DateTimeParseException.class,})
    ResponseEntity<Object> handleIncorrectDateInput(RuntimeException ex, HttpServletResponse response) throws IOException {
        ex.printStackTrace();
        //todo note that when there is no below single line of code then swagger-ui doesnt see response body and status code
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //has to return ModelAndView because swagger doesnt handle response entity :(
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody ResponseEntity<Object> handleEmptyDto(HttpMessageNotReadableException ex,
                                                        HttpServletResponse response) throws IOException {
        ex.printStackTrace();
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {JsonParseException.class})
    @ResponseBody ResponseEntity<String> handleWrongJsonInput(RuntimeException ex,
                                                              HttpServletResponse response) throws IOException {
        ex.printStackTrace();
        response.sendError(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY);

    }
    //NoSuchElementException

    @ExceptionHandler(value = {NoSuchElementException.class})
    @ResponseBody ResponseEntity<Object> handleWrongGivenId(NoSuchElementException ex,
                                                            HttpServletResponse response) throws IOException {
        ex.printStackTrace();
        response.sendError(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);

    }

    //only for debugging purposes
    @ExceptionHandler(value = Throwable.class)
    ResponseEntity<Object> printStackTrace(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

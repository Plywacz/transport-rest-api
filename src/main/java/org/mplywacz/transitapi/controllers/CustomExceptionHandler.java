package org.mplywacz.transitapi.controllers;
/*
Author: BeGieU
Date: 09.07.2019
*/

import com.fasterxml.jackson.core.JsonParseException;
import org.mplywacz.transitapi.exceptions.EntityNotFoundException;
import org.mplywacz.transitapi.exceptions.IllegalDateInputException;
import org.mplywacz.transitapi.exceptions.IncorrectLocationException;
import org.mplywacz.transitapi.exceptions.UnprocessableRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

//todo fix unsuitable HttpsStatus in response entity

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = IncorrectLocationException.class)
    ResponseEntity<Object> handleIncorrectLocation(IncorrectLocationException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = UnprocessableRequestException.class)
    ResponseEntity<Object> handleUnprocessableRequest(UnprocessableRequestException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

   /*todo consider handling this error or leaving default spring's solution
   Doesnt work: solution: https://stackoverflow.com/questions/16651160/spring-rest-errorhandling-controlleradvice-valid
    */

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    ResponseEntity<Object> handleDtoValidation(MethodArgumentNotValidException ex) {
//        ex.printStackTrace();
//        var errors = new HashMap<String, String>();
//        ex.getBindingResult().getAllErrors().forEach(er -> {
//            String fieldName = ((FieldError) er).getField();
//            String errorMessage = er.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
//    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalDateInputException.class,
            DateTimeParseException.class,})
    ResponseEntity<Object> handleIncorrectDateInput(RuntimeException ex) throws IOException {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    //has to return ModelAndView because swagger doesnt handle response entity :(
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody ResponseEntity<Object> handleEmptyDto(HttpMessageNotReadableException ex)  {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage().split(":")[0],
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = {JsonParseException.class})
    @ResponseBody ResponseEntity<String> handleWrongJsonInput(RuntimeException ex, HttpServletResponse response) throws IOException {
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
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    @ResponseBody ResponseEntity<String> handleEntityDoesntExistInDb(RuntimeException ex, HttpServletResponse response) throws IOException {
        ex.printStackTrace();
        response.sendError(HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    @ResponseBody ResponseEntity<String> handleWrongPathVariable(RuntimeException ex, HttpServletResponse response) throws IOException {
        ex.printStackTrace();
        response.sendError(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.BAD_REQUEST);

    }

    //only for debugging purposes
    @ExceptionHandler(value = Throwable.class)
    ResponseEntity<Object> printStackTrace(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

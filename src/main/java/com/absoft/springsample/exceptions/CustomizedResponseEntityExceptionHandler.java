package com.absoft.springsample.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ExceptionResponseModel exceptionResponseModel = new ExceptionResponseModel(new Date(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundExceptions(NotFoundException exception, WebRequest request) {
        ExceptionResponseModel exceptionResponseModel = new ExceptionResponseModel(new Date(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponseModel, HttpStatus.NOT_FOUND);
    }

}

package com.example.bank.configurations;

import com.example.bank.exceptions.InsufficientFoundsException;
import com.example.bank.exceptions.NotFoundException;
import com.example.bank.exceptions.ResponseErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ResponseErrorMessage> notFoundHandler(NotFoundException exception){
        ResponseErrorMessage errorMessage = new ResponseErrorMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    @ExceptionHandler(InsufficientFoundsException.class)
    private ResponseEntity<ResponseErrorMessage> insufficientFoundsHandler(InsufficientFoundsException exception){
        ResponseErrorMessage errorMessage = new ResponseErrorMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorMessage);
    }
}

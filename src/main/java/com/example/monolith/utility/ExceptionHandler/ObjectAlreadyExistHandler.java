package com.example.monolith.utility.ExceptionHandler;

import com.example.monolith.utility.Exceptions.ObjectAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ObjectAlreadyExistHandler {


    @ExceptionHandler(value = {ObjectAlreadyExistException.class})
    public ResponseEntity<ErrorMessage> handleStudentNotAssigned(ObjectAlreadyExistException ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setMessage(ex.getMessage());
        errorMessage.setTimeStamp(LocalDateTime.now());
        errorMessage.setStatus(HttpStatus.NOT_ACCEPTABLE);

        return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

    }
}
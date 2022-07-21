package com.example.monolith.utility.ExceptionHandler;

import com.example.monolith.utility.Exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ObjectNotFoundHandler {


    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleObjectNotFound(ObjectNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setMessage(ex.getMessage());
        errorMessage.setTimeStamp(LocalDateTime.now());
        errorMessage.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

    }
}
package com.example.monolith.utility.ExceptionHandler;

import com.example.monolith.utility.Exceptions.EmptyDatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class EmptyDatabaseHandler {


    @ExceptionHandler(value = {EmptyDatabaseException.class})
    public ResponseEntity<ErrorMessage> handleEmptyDatabase(EmptyDatabaseException ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.setMessage(ex.getMessage());
        errorMessage.setTimeStamp(LocalDateTime.now());
        errorMessage.setStatus(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(errorMessage, errorMessage.getStatus());

    }
}
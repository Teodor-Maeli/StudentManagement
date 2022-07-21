package com.example.monolith.utility.ExceptionHandler;

import com.example.monolith.utility.Exceptions.StudentNotAssignedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class StudentNotAssignedHandler {


        @ExceptionHandler(value={StudentNotAssignedException.class})
    public ResponseEntity<ErrorMessage> handleStudentNotAssigned(StudentNotAssignedException ex){
            ErrorMessage errorMessage = new ErrorMessage();

            errorMessage.setMessage(ex.getMessage());
            errorMessage.setTimeStamp(LocalDateTime.now());
            errorMessage.setStatus(HttpStatus.CONFLICT);

            return new ResponseEntity<>(errorMessage,errorMessage.getStatus());

        }

    }

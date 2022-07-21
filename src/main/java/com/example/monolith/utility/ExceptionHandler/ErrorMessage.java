package com.example.monolith.utility.ExceptionHandler;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class ErrorMessage {
    private LocalDateTime timeStamp;
    private HttpStatus status;
    private String message;


}


package com.example.monolith.utility.Exceptions;


public class StudentNotAssignedException extends RuntimeException {
    public StudentNotAssignedException(String message){
        super(message);
    }
}

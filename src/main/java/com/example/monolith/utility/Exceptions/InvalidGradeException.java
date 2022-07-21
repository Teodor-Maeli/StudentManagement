package com.example.monolith.utility.Exceptions;




public class InvalidGradeException extends RuntimeException {

    public InvalidGradeException(String message){
        super(message);
    }
}

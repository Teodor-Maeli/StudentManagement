package com.example.monolith.utility.Exceptions;



public class EmptyDatabaseException extends RuntimeException {

    public EmptyDatabaseException(String message){
        super(message);
    }

}

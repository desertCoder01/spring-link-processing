package com.aditya.codes.process.exception;

public class InvalidRequestException extends RuntimeException {

    private String message;

    public InvalidRequestException(String message){
        super(message);
        this.message = message;
    }

}

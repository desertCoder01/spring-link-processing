package com.aditya.codes.process.exception;

public class CustomRuntimeException extends RuntimeException {
    private String message;

    public CustomRuntimeException(String message){
        super(message);
        this.message=message;
    }
}

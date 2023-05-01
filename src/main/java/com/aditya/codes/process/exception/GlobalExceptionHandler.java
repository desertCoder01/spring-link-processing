package com.aditya.codes.process.exception;

import com.aditya.codes.process.codes.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Pankaj.Kumar on 4/11/2022
 */


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiResponse> InvalidRequestExceptionHandler(InvalidRequestException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<ApiResponse> customRuntimeException(CustomRuntimeException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseEntity<ApiResponse> customUnauthorizedException(CustomUnauthorizedException ex){
        String message = ex.getMessage();
        ApiResponse response = new ApiResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}

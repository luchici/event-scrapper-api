package com.example.springboot99.controlers;

import com.example.springboot99.exception.CityErrorResponse;
import com.example.springboot99.exception.CityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler
    public ResponseEntity<CityErrorResponse> handleException(CityNotFoundException exc) {
        CityErrorResponse error = new CityErrorResponse();
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exc.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
}

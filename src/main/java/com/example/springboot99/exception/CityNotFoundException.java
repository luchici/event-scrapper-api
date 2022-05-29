package com.example.springboot99.exception;

public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException(String message){
        super(message);
    }

    public CityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityNotFoundException(Throwable cause) {
        super(cause);
    }

}

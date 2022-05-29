package com.example.springboot99.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class CityErrorResponse {

    private int status;
    private String message;
    private Instant creationTime = Instant.now();


}

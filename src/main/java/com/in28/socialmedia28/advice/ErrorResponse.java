package com.in28.socialmedia28.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private LocalDateTime timeStamp;

    public ErrorResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus status) {
        this();
        this.status = status;
    }

    public ErrorResponse(HttpStatus status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(HttpStatus status, LocalDateTime timeStamp, String message) {
        this();
        this.status = status;
        this.timeStamp = timeStamp;
        this.message = message;
    }
}

package com.springboot.student.exception;

import org.springframework.http.HttpStatus;

public class StudentdetailsAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public StudentdetailsAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public StudentdetailsAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

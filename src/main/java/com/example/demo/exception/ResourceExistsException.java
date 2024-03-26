package com.example.demo.exception;

public class ResourceExistsException extends RuntimeException{
    public ResourceExistsException() {
        super();
    }

    public ResourceExistsException(String message) {
        super(message);
    }
}

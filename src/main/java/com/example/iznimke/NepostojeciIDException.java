package com.example.iznimke;

public class NepostojeciIDException extends RuntimeException {

    public NepostojeciIDException() {
    }

    public NepostojeciIDException(String message) {
        super(message);
    }

    public NepostojeciIDException(String message, Throwable cause) {
        super(message, cause);
    }
}

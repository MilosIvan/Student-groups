package com.example.iznimke;

public class NeispravanProsjekException extends RuntimeException {
    public NeispravanProsjekException() {
    }
    public NeispravanProsjekException(String message) {
        super(message);
    }
    public NeispravanProsjekException(String message, Throwable cause) {
        super(message, cause);
    }
}

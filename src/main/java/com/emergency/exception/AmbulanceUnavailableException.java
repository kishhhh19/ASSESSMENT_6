package com.emergency.exception;

public class AmbulanceUnavailableException extends RuntimeException {
    public AmbulanceUnavailableException(String message) {
        super(message);
    }
}

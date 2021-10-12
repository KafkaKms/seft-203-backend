package com.kms.seft203.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException() {
        super("No data found.");
    }

    public DataNotFoundException(String message) {
        super(message);
    }
}

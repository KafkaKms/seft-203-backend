package com.kms.seft203.exceptions;

public class DuplicateException extends RuntimeException {
    public DuplicateException() {
        super("Duplicate error");
    }

    public DuplicateException(String message) {
        super(message);
    }
}

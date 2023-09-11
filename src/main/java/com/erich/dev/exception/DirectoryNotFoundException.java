package com.erich.dev.exception;

public class DirectoryNotFoundException extends RuntimeException {

    public DirectoryNotFoundException() {
        super();
    }

    public DirectoryNotFoundException(String message) {
        super(message);
    }
}

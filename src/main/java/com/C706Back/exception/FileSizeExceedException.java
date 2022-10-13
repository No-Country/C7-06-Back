package com.C706Back.exception;

public class FileSizeExceedException extends RuntimeException {

    private static final Long serialVersionUID = 1L;
    private String message;

    public FileSizeExceedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.epam.esm.entity;

public class ErrorData {

    private String message;
    private int errorCode;

    public ErrorData(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

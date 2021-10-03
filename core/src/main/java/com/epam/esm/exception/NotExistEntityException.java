package com.epam.esm.exception;

public class NotExistEntityException extends RuntimeException {

    public NotExistEntityException(String message) {
        super(message);
    }
}

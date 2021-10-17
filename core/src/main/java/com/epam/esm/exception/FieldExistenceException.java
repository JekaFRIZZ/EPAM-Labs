package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FieldExistenceException extends CustomException {
    public FieldExistenceException(String message) {
        super(message);
    }

    public FieldExistenceException(String message, int errorCode) {
        super(message, errorCode);
    }

    public FieldExistenceException() {
    }
}

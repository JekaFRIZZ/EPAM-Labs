package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
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

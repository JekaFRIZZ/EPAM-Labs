package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ValidationException extends CustomException {
    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, int errorCode) {
        super(message, errorCode);
    }

    public ValidationException() {
    }
}

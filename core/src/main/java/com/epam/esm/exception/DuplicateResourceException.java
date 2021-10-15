package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateResourceException extends CustomException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String message, int errorCode) {
        super(message, errorCode);
    }

    public DuplicateResourceException() {
    }
}

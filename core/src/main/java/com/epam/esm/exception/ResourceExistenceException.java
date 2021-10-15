package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceExistenceException extends CustomException {

    public ResourceExistenceException(String message) {
        super(message);
    }

    public ResourceExistenceException(String message, int errorCode) {
        super(message, errorCode);
    }

    public ResourceExistenceException() {
    }
}

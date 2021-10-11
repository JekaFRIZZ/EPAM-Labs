package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceExistenceException extends RuntimeException {

    public ResourceExistenceException(String message) {
        super(message);
    }
}

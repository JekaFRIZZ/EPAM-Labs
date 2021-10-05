package com.epam.esm.util;

import com.epam.esm.entity.ErrorData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorUtils {

    public static ResponseEntity<?> createResponseEntityForCustomError(String message ,int errorCode,HttpStatus httpStatus) {
        ErrorData errorData = new ErrorData(message, errorCode);
        return new ResponseEntity<>(errorData, httpStatus);
    }
}

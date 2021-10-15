package com.epam.esm.exception;

import com.epam.esm.entity.ErrorData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(ResourceExistenceException.class)
    public @ResponseBody ErrorData handleResourceNotFoundException(CustomException e){
        return new ErrorData(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public @ResponseBody ErrorData handleResourceNotUniqueException(CustomException e){
        return new ErrorData(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(ValidationException.class)
    public @ResponseBody ErrorData handleValidationException(CustomException e){
        return new ErrorData(e.getMessage(), e.getErrorCode());
    }

    @ExceptionHandler(FieldExistenceException.class)
    public @ResponseBody ErrorData handleFieldExistException(CustomException e){
        return new ErrorData(e.getMessage(), e.getErrorCode());
    }
}

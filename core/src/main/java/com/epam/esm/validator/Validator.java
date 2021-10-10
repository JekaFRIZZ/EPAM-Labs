package com.epam.esm.validator;

public interface Validator<T> {
    void validate(T entity);
}

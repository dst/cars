package com.stefanski.cars.util;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

/**
 * @author Dariusz Stefanski
 */
@RequiredArgsConstructor
class ParameterError {

    private final String field;
    private final String message;

    public static ParameterError fromFieldError(FieldError error) {
        return new ParameterError(error.getField(), error.getDefaultMessage());
    }

    public String getDescription() {
        return String.format("%s %s", field, message);
    }
}

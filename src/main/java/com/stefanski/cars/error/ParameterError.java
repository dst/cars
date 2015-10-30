package com.stefanski.cars.error;

import lombok.Data;
import org.springframework.validation.FieldError;

/**
 * @author Dariusz Stefanski
 */
@Data
class ParameterError {
    private String field;
    private String message;

    public static ParameterError fromFieldError(FieldError error) {
        return new ParameterError(error.getField(), error.getDefaultMessage());
    }

    public ParameterError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getDescription() {
        return String.format("%s %s", field, message);
    }
}

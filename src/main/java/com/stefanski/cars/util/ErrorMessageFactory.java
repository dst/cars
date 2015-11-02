package com.stefanski.cars.util;

import org.springframework.validation.BindingResult;

import static java.util.stream.Collectors.joining;

/**
 * @author Dariusz Stefanski
 */
public class ErrorMessageFactory {

    public static String fromFailedValidation(BindingResult result) {
        return result.getFieldErrors().stream()
                .map(ParameterError::fromFieldError)
                .map(ParameterError::getDescription)
                .collect(joining(";"));
    }
}

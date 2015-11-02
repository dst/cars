package com.stefanski.cars.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.stefanski.cars.api.ErrorResp;
import com.stefanski.cars.util.ErrorMessageFactory;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Dariusz Stefanski
 */
@ControllerAdvice
@Slf4j
class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResp> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        log.warn("Validation error: {}", result);
        String message = ErrorMessageFactory.fromFailedValidation(result);
        ErrorResp error = new ErrorResp("Invalid parameter", message, BAD_REQUEST);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}

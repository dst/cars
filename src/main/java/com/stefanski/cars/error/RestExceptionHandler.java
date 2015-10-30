package com.stefanski.cars.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.stefanski.cars.store.CarNotFoundException;

import static com.stefanski.cars.error.ErrorResp.*;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Dariusz Stefanski
 */
@Slf4j
@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorResp> handleCarNotFoundException(CarNotFoundException ex) {
        log.warn("Car not found: {}", ex.getMessage());
        ErrorResp error = new ErrorResp(RESOURCE_NOT_FOUND_ERR, ex.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(error, NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResp> handleValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        log.warn("Validation error: {}", result);

        String message = result.getFieldErrors()
                .stream()
                .map(ParameterError::fromFieldError)
                .map(ParameterError::getDescription)
                .collect(joining(";"));

        ErrorResp error = new ErrorResp(INVALID_PARAM_ERR, message, BAD_REQUEST);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<ErrorResp> handleTypeMismatchException(TypeMismatchException ex) {
        log.warn("Invalid type of parameter: {}", ex.getMessage());
        ErrorResp error = new ErrorResp(INVALID_TYPE_ERR, INVALID_TYPE_ERR, BAD_REQUEST);
        return new ResponseEntity<>(error, BAD_REQUEST);
    }
}

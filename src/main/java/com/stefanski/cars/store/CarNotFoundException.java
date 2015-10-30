package com.stefanski.cars.store;

/**
 * Thrown when car was not found.
 *
 * @author Dariusz Stefanski
 */
public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(String message) {
        super(message);
    }
}

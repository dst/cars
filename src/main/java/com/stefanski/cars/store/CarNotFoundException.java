package com.stefanski.cars.store;

/**
 * Thrown when car was not found.
 *
 * @author Dariusz Stefanski
 */
public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException(Long carId) {
        super(String.format("Car with id %d does not exist", carId));
    }
}

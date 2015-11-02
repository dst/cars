package com.stefanski.cars.api;

import java.util.Map;

import com.wordnik.swagger.annotations.ApiModel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import com.stefanski.cars.store.Car;

/**
 * @author Dariusz Stefanski
 */
@ApiModel
@RequiredArgsConstructor
@Value
public class CarResource {

    private final String make;
    private final String model;
    private final Integer year;
    private final Integer engineDisplacement;
    private final Map<String, String> attributes;

    public static CarResource fromCar(Car car) {
        return new CarResource(car.getMake(), car.getModel(), car.getYear(),
                car.getEngineDisplacement(), car.getAttributesMap());
    }
}

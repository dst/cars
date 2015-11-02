package com.stefanski.cars.search;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stefanski.cars.store.Car;
import com.stefanski.cars.store.CarStore;

import static java.util.stream.Collectors.toList;

/**
 * @author Dariusz Stefanski
 */
@Component
public class CarFinder {

    private AttributeStore attributeStore;
    private CarStore carStore;

    @Autowired
    public CarFinder(AttributeStore attributeStore, CarStore carStore) {
        this.attributeStore = attributeStore;
        this.carStore = carStore;
    }

    public List<Car> find(Map<String, String> filters) {
        List<Long> cardIds = attributeStore.findCars(filters);
        // I assume a high hit ratio in cars cache, otherwise it'd inefficient and
        // and it'd be better to fetch all cars at once from db
        return cardIds.stream().map(carStore::findCar).collect(toList());
    }
}

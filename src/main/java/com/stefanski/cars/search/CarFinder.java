package com.stefanski.cars.search;

import java.util.List;

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

    private AttributeSearchStore attributeSearchStore;
    private CarStore carStore;

    @Autowired
    public CarFinder(AttributeSearchStore attributeSearchStore, CarStore carStore) {
        this.attributeSearchStore = attributeSearchStore;
        this.carStore = carStore;
    }

    public List<Car> find(CarFilters filters) {
        List<Long> cardIds = attributeSearchStore.findCars(filters.getAttributes());
        // I assume a high hit ratio in cars cache, otherwise it'd inefficient and
        // and it'd be better to fetch all cars at once from db
        return cardIds.stream().map(carStore::findCar).collect(toList());
    }
}

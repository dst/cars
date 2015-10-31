package com.stefanski.cars.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dariusz Stefanski
 */
@Slf4j
@Transactional
@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car findCar(Long carId) {
        Car car = carRepository.findOne(carId);
        if (car == null) {
            throw new CarNotFoundException(carId);
        }
        log.trace("Found car: {}", car);
        return car;
    }

    public Long createCar(Car car) {
        Car createdCar = carRepository.save(car);
        log.debug("Created car: {}", createdCar);
        return createdCar.getId();
    }

    public void updateCar(Car car) {
        throwIfNoSuchCar(car.getId());
        carRepository.save(car);
    }

    public void deleteCar(Long carId) {
        throwIfNoSuchCar(carId);
        carRepository.delete(carId);
    }

    private void throwIfNoSuchCar(Long carId) {
        if (!carRepository.exists(carId)) {
            throw new CarNotFoundException(carId);
        }
    }
}

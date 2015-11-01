package com.stefanski.cars.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.stefanski.cars.store.CacheConfiguration.CAR_CACHE;

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

    @Cacheable(CAR_CACHE)
    public Car findCar(Long carId) {
        Car car = carRepository.findOne(carId);
        if (car == null) {
            throw new CarNotFoundException(carId);
        }
        log.debug("Found car: {}", car);
        return car;
    }

    public Long createCar(Car car) {
        Car createdCar = carRepository.save(car);
        log.debug("Created car: {}", createdCar);
        return createdCar.getId();
    }

    @CacheEvict(value = CAR_CACHE, key = "#car.id")
    public void updateCar(Car car) {
        throwIfNoSuchCar(car.getId());
        carRepository.save(car);
        log.debug("Updated car: {}", car);
    }

    @CacheEvict(CAR_CACHE)
    public void deleteCar(Long carId) {
        throwIfNoSuchCar(carId);
        carRepository.delete(carId);
        log.debug("Deleted car: {}", carId);
    }

    private void throwIfNoSuchCar(Long carId) {
        if (!carRepository.exists(carId)) {
            throw new CarNotFoundException(carId);
        }
    }
}

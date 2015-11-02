package com.stefanski.cars.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.stefanski.cars.store.events.DeletedCarEvent;
import com.stefanski.cars.store.events.NewCarEvent;
import com.stefanski.cars.store.events.UpdatedCarEvent;

import static com.stefanski.cars.store.CacheConfiguration.CAR_CACHE;

/**
 * @author Dariusz Stefanski
 */
@Slf4j
@Transactional
@Component
public class CarStore {

    private final CarRepository carRepository;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public CarStore(CarRepository carRepository, ApplicationEventPublisher publisher) {
        this.carRepository = carRepository;
        this.publisher = publisher;
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
        publisher.publishEvent(new NewCarEvent(createdCar));
        return createdCar.getId();
    }

    @CacheEvict(value = CAR_CACHE, key = "#car.id")
    public void updateCar(Car car) {
        throwIfNoSuchCar(car.getId());
        carRepository.save(car);
        publisher.publishEvent(new UpdatedCarEvent(car));
        log.debug("Updated car: {}", car);
    }

    @CacheEvict(CAR_CACHE)
    public void deleteCar(Long carId) {
        throwIfNoSuchCar(carId);
        carRepository.delete(carId);
        publisher.publishEvent(new DeletedCarEvent(carId));
        log.debug("Deleted car: {}", carId);
    }

    private void throwIfNoSuchCar(Long carId) {
        if (!carRepository.exists(carId)) {
            throw new CarNotFoundException(carId);
        }
    }
}

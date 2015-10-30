package com.stefanski.cars.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dariusz Stefanski
 */
@Slf4j
@Transactional
@Service
class CarService {

    private CarRepository carRepository;
    private AttributeRepository attributeRepository;

    @Autowired
    public CarService(CarRepository carRepository, AttributeRepository attributeRepository) {
        this.carRepository = carRepository;
        this.attributeRepository = attributeRepository;
    }

    public Car findById(Long carId) {
        Car car = carRepository.findOne(carId);
        if (car == null) {
            String msg = String.format("Car with id %d does not exist", carId);
            throw new CarNotFoundException(msg);
        }
        log.trace("Found car: {}", car);
        return car;
    }

    public Long create(CarDto carDto) {
        Car car = createCarFromDto(carDto);
        Car createdCar = carRepository.save(car);
        createCarAttributes(carDto, createdCar);
        log.debug("Created car: {}", createdCar);
        return createdCar.getId();
    }

    public void update(Long carId, CarDto carDto) {
        Car car = findById(carId);
        Car updatedCar = createCarFromDto(carDto);
        createCarAttributes(carDto, car);
        //TODO(dst), 31.10.15: remove attributes, create attributes
        BeanUtils.copyProperties(updatedCar, car, "id");
        carRepository.save(updatedCar);
    }

    private Car createCarFromDto(CarDto carDto) {
        Car car = new Car();
        BeanUtils.copyProperties(carDto, car);
        return car;
    }

    private void createCarAttributes(CarDto carDto, Car createdCar) {
        carDto.getAttributes().forEach((name, value) -> {
            Attribute attribute = new Attribute(name, value.toString());
            attribute.setCar(createdCar);
            attributeRepository.save(attribute);
        });
    }

    public void deleteCar(Long carId) {
        //TODO(dst), 31.10.15: use exists
        Car car = findById(carId);
        carRepository.delete(car);
    }
}

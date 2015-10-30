package com.stefanski.cars.store

import spock.lang.Specification

import static com.stefanski.cars.store.CarFixture.CAR_ID
import static com.stefanski.cars.store.CarFixture.OPEL_CORSA

/**
 * @author Dariusz Stefanski
 */
class CarServiceSpec extends Specification {

    def 'should find car by id'() {
        given:
            Car car = new Car(id: CAR_ID)
            CarRepository carRepository = Stub()
            carRepository.findOne(CAR_ID) >> car
            CarService carService = new CarService(carRepository, null)
        when:
            Car foundCar = carService.findById(CAR_ID)
        then:
            foundCar.id == car.id
    }

    def 'should throw exception when car doesnt exist'() {
        given:
            CarRepository carRepository = Stub()
            carRepository.findOne(_) >> null
            CarService carService = new CarService(carRepository, null)
        when:
            carService.findById(CAR_ID)
        then:
            thrown(CarNotFoundException)
    }

    def 'should return id of created car'() {
        given:
            Car car = new Car(id: CAR_ID)
            CarRepository carRepository = Stub()
            carRepository.save(_) >> car
            CarService carService = new CarService(carRepository, Stub(AttributeRepository))
        expect:
            carService.create(OPEL_CORSA) == CAR_ID
    }
}

package com.stefanski.cars.store

import com.stefanski.cars.search.AttributeSearchStore
import spock.lang.Specification

/**
 * @author Dariusz Stefanski
 */
class CarServiceSpec extends Specification {

    static final long CAR_ID = 123L

    CarService carService
    CarRepository carRepository = Mock()

    def setup() {
        carService = new CarService(carRepository, Stub(AttributeSearchStore))
    }

    def 'should throw exception when finding car which does not exist'() {
        given:
            carRepository.findOne(_) >> null
        when:
            carService.findCar(CAR_ID)
        then:
            thrown(CarNotFoundException)
    }

    def 'should throw exception when deleting car which does not exist'() {
        given:
            carRepository.findOne(_) >> null
        when:
            carService.deleteCar(CAR_ID)
        then:
            thrown(CarNotFoundException)
    }

    def 'should throw exception when updating car which does not exist'() {
        given:
            carRepository.findOne(_) >> null
        when:
            carService.updateCar(new Car())
        then:
            thrown(CarNotFoundException)
    }

    def 'should find car by id'() {
        given:
            Car car = new Car(id: CAR_ID)
            carRepository.findOne(CAR_ID) >> car
        when:
            Car foundCar = carService.findCar(CAR_ID)
        then:
            foundCar.id == car.id
    }

    def 'should return id of created car'() {
        given:
            Car car = new Car(id: CAR_ID)
            carRepository.save(_) >> car
        expect:
            carService.createCar(car) == CAR_ID
    }

    def 'should update car'() {
        given:
            Car car = new Car(id: CAR_ID)
            carRepository.exists(CAR_ID) >> true
        when:
            carService.updateCar(car)
        then:
            1 * carRepository.save(car)
    }

    def 'should delete car'() {
        given:
            carRepository.exists(CAR_ID) >> true
        when:
            carService.deleteCar(CAR_ID)
        then:
            1 * carRepository.delete(CAR_ID)
    }
}
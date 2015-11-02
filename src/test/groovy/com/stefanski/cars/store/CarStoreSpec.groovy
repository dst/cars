package com.stefanski.cars.store

import org.springframework.context.ApplicationEventPublisher
import spock.lang.Specification

/**
 * @author Dariusz Stefanski
 */
class CarStoreSpec extends Specification {

    static final long CAR_ID = 123L

    CarStore carService
    CarRepository carRepository = Mock()
    ApplicationEventPublisher publisher = Mock()

    def setup() {
        carService = new CarStore(carRepository, publisher)
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

    def 'should return id of created car and publish event'() {
        given:
            Car car = new Car(id: CAR_ID)
            carRepository.save(_) >> car
        when:
            def carId = carService.createCar(car)
        then:
            carId == CAR_ID
            1 * publisher.publishEvent(_ as NewCarEvent)
    }

    def 'should update car and publish event'() {
        given:
            Car car = new Car(id: CAR_ID)
            carRepository.exists(CAR_ID) >> true
        when:
            carService.updateCar(car)
        then:
            1 * carRepository.save(car)
            1 * publisher.publishEvent(_ as UpdatedCarEvent)

    }

    def 'should delete car and publish event'() {
        given:
            carRepository.exists(CAR_ID) >> true
        when:
            carService.deleteCar(CAR_ID)
        then:
            1 * carRepository.delete(CAR_ID)
            1 * publisher.publishEvent(_ as DeletedCarEvent)
    }
}
package com.stefanski.cars.store.rest

import com.stefanski.cars.store.Car
import com.stefanski.cars.store.rest.CarResource
import spock.lang.Specification

/**
 * @author Dariusz Stefanski
 */
class CarResourceSpec extends Specification {

    def 'should convert car to resource'() {
        given:
            Car car = new Car(make: 'Opel', model: 'Corsa')
        when:
            CarResource resource = CarResource.fromCar(car)
        then:
            resource.make == 'Opel'
            resource.model == 'Corsa'
    }
}

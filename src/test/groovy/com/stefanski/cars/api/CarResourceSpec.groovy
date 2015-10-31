package com.stefanski.cars.api

import com.stefanski.cars.store.Car
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

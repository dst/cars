package com.stefanski.cars.api

import com.stefanski.cars.store.Car
import com.stefanski.cars.api.CarResource
import spock.lang.Specification

import static com.stefanski.cars.api.CarResourceExamples.OPEL_CORSA_RESOURCE

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

    def "should convert resource to car"() {
        when:
            Car car = OPEL_CORSA_RESOURCE.toCar()
        then:
            car.make == OPEL_CORSA_RESOURCE.make
            car.model == OPEL_CORSA_RESOURCE.model
            car.attributes.size() == OPEL_CORSA_RESOURCE.attributes.size()
    }
}

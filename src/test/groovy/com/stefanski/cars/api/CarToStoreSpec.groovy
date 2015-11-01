package com.stefanski.cars.api

import com.stefanski.cars.store.Car
import spock.lang.Specification

import static CarToStoreExamples.OPEL_CORSA_TO_STORE

/**
 * @author Dariusz Stefanski
 */
class CarToStoreSpec extends Specification {

    def "should convert to car"() {
        when:
            Car car = OPEL_CORSA_TO_STORE.toCar()
        then:
            car.make == OPEL_CORSA_TO_STORE.make
            car.model == OPEL_CORSA_TO_STORE.model
            car.attributes.size() == OPEL_CORSA_TO_STORE.attributes.size()
    }
}

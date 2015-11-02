package com.stefanski.cars.store

import com.stefanski.cars.store.rest.CarToStoreExamples
import spock.lang.Specification

/**
 * @author Dariusz Stefanski
 */
class CarToStoreSpec extends Specification {

    def "should convert to car"() {
        when:
            Car car = CarToStoreExamples.OPEL_CORSA_TO_STORE.toCar()
        then:
            car.make == CarToStoreExamples.OPEL_CORSA_TO_STORE.make
            car.model == CarToStoreExamples.OPEL_CORSA_TO_STORE.model
            car.attributes.size() == CarToStoreExamples.OPEL_CORSA_TO_STORE.attributes.size()
    }
}

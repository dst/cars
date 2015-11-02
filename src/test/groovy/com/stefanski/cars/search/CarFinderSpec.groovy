package com.stefanski.cars.search

import com.stefanski.cars.store.CarStore
import spock.lang.Specification

import static com.stefanski.cars.store.CarExamples.OPEL_CORSA

/**
 * @author Dariusz Stefanski
 */
class CarFinderSpec extends Specification {

    def "should find car using all its attributes as filter"() {
        given:
            def attributes = OPEL_CORSA.attributesMap
            AttributeStore attributeSearchStore = Stub(AttributeStore)
            attributeSearchStore.findCars(attributes) >> [OPEL_CORSA.id]
            CarStore carStore = Stub(CarStore)
            carStore.findCar(OPEL_CORSA.id) >> OPEL_CORSA
            CarFinder carFinder = new CarFinder(attributeSearchStore, carStore)
        when:
            def cars = carFinder.find(attributes)
        then:
            cars[0] == OPEL_CORSA
    }
}

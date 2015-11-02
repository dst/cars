package com.stefanski.cars.search

import com.stefanski.cars.store.CarStore
import spock.lang.Specification

import static com.stefanski.cars.store.CarExamples.OPEL_CORSA

/**
 * @author Dariusz Stefanski
 */
class CarFinderSpec extends Specification {

    def "should find car"() {
        given:
            def attributes = OPEL_CORSA.attributesMap
            AttributeSearchStore attributeSearchStore = Stub(AttributeSearchStore)
            attributeSearchStore.findCars(attributes) >> [OPEL_CORSA.id]
            CarStore carStore = Stub(CarStore)
            carStore.findCar(OPEL_CORSA.id) >> OPEL_CORSA
            CarFinder carFinder = new CarFinder(attributeSearchStore, carStore)
        when:
            def filters = new CarFilters(attributes: attributes)
            def cars = carFinder.find(filters)
        then:
            cars[0] == OPEL_CORSA
    }
}

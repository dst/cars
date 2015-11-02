package com.stefanski.cars.search

import com.stefanski.cars.CarApplication
import com.stefanski.cars.store.Attribute
import com.stefanski.cars.store.Car
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

import static com.stefanski.cars.store.CarExamples.NISSAN_PRIMERA
import static com.stefanski.cars.store.CarExamples.OPEL_CORSA

/**
 * @author Dariusz Stefanski
 */
@IntegrationTest
@WebAppConfiguration
@ContextConfiguration(classes = [CarApplication], loader = SpringApplicationContextLoader)
class AttributeSearchStoreIntegrationSpec extends Specification {

    @Autowired
    AttributeSearchStore attributeSearchStore

    List insertedCars

    def setup() {
        insertedCars = []
    }

    def cleanup() {
        insertedCars.each {
            attributeSearchStore.deleteAttributesOf(it.id)
        }
    }

    private insertCar(Car car) {
        attributeSearchStore.insertAttributesOf(car)
        insertedCars << car
    }

    def "should find car by its attributes"() {
        given:
            insertCar(OPEL_CORSA)
        when:
            def cars = attributeSearchStore.findCars(OPEL_CORSA.getAttributesMap())
        then:
            cars.size() == 1
            cars[0] == OPEL_CORSA.id
    }

    def "should find updated car by its attributes"() {
        given:
            Car car = OPEL_CORSA
            insertCar(car)
            car.attributes = [new Attribute('updated', 'true')]
            attributeSearchStore.updateAttributesOf(car)
        when:
            def cars = attributeSearchStore.findCars(car.getAttributesMap())
        then:
            cars.size() == 1
            cars[0] == car.id
    }

    def "should find all cars using empty filter"() {
        given:
            insertCar(OPEL_CORSA)
            insertCar(NISSAN_PRIMERA)
        when:
            def cars = attributeSearchStore.findCars([:])
        then:
            cars.size() == 2
    }

    def "should not find deleted car"() {
        given:
            insertCar(OPEL_CORSA)
            attributeSearchStore.deleteAttributesOf(OPEL_CORSA.id)
        when:
            def cars = attributeSearchStore.findCars([:])
        then:
            cars.isEmpty()
    }
}

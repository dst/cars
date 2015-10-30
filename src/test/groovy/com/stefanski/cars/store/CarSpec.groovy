package com.stefanski.cars.store

import spock.lang.Specification

/**
 * @author Dariusz Stefanski
 */
class CarSpec extends Specification {

    def 'should return correct map for 1 attribute'() {
        given:
            Car car = new Car(attributes: [new Attribute('length', '500')])
        expect:
            car.getAttributesMap() == ['length': '500']
    }

    def 'should return empty map when there are no attributes'() {
        given:
            Car car = new Car()
        expect:
            car.getAttributesMap() == [:]
    }
}

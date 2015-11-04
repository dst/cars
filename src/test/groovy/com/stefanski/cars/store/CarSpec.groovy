package com.stefanski.cars.store

import spock.lang.Specification

import static com.stefanski.cars.store.CarExamples.OPEL_CORSA

/**
 * @author Dariusz Stefanski
 */
class CarSpec extends Specification {

    def "should return correct map for 1 attribute"() {
        given:
            Car car = new Car(attributes: [new Attribute('length', '500')])
        expect:
            car.getAttributesMap() == ['length': '500']
    }

    def "should return empty map when there are no attributes"() {
        given:
            Car car = new Car()
        expect:
            car.getAttributesMap() == [:]
    }

    def "should return all basic fields in map"() {
        when:
            def fieldsMap = OPEL_CORSA.getBasicFieldsMap()
        then:
            fieldsMap['model'] == OPEL_CORSA.model
            fieldsMap['make'] == OPEL_CORSA.make
            fieldsMap['year'] == OPEL_CORSA.year
            fieldsMap['engineDisplacement'] == OPEL_CORSA.engineDisplacement
    }
}

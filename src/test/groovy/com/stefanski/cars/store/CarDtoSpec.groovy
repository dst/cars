package com.stefanski.cars.store

import spock.lang.Specification

/**
 * @author Dariusz Stefanski
 */
class CarDtoSpec extends Specification {

    def 'should convert car to dto'() {
        given:
            Car car = new Car(make: 'Opel', model: 'Corsa')
        when:
            CarDto carDto = CarDto.fromCar(car)
        then:
            carDto.make == 'Opel'
            carDto.model == 'Corsa'
    }
}

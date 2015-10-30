package com.stefanski.cars.store
/**
 * @author Dariusz Stefanski
 */
class CarFixture {

    public static final long CAR_ID = 123L

    static final CarDto OPEL_CORSA = new CarDto(
            make: 'Opel', model: 'Corsa', year: 2010, engineDisplacement: 1300,
            attributes: ['origin': 'Poland', 'mileage': '90120']
    );

    static final CarDto CAR_WITHOUT_MODEL = new CarDto(
            make: 'Opel', year: 2010, engineDisplacement: 1300,
            attributes: ['origin': 'Poland', 'mileage': '90120']
    );
}

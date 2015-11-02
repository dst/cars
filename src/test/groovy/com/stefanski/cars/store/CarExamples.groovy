package com.stefanski.cars.store;

/**
 * @author Dariusz Stefanski
 */
class CarExamples {

    static final Car OPEL_CORSA = new Car(id: 1L,
            make: 'Opel', model: 'Corsa', year: 2010, engineDisplacement: 1300,
            attributes: [
                    new Attribute('origin', 'Poland'),
                    new Attribute('mileage', '90120')
            ]
    );

    static final Car NISSAN_PRIMERA = new Car(id: 2L,
            make: 'Nissan', model: 'Primera', year: 1991, engineDisplacement: 1600,
            attributes: [
                    new Attribute('origin', 'Germany'),
            ]
    );
}

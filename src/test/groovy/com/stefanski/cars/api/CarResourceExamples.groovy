package com.stefanski.cars.api


/**
 * @author Dariusz Stefanski
 */
class CarResourceExamples {

    static final CarResource OPEL_CORSA_RESOURCE = new CarResource(
            make: 'Opel', model: 'Corsa', year: 2010, engineDisplacement: 1300,
            attributes: ['origin': 'Poland', 'mileage': '90120']
    );

    static final CarResource CAR_WITHOUT_MODEL_RESOURCE = new CarResource(
            make: 'Opel', year: 2010, engineDisplacement: 1300,
            attributes: ['origin': 'Poland', 'mileage': '90120']
    );
}

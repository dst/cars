package com.stefanski.cars.api

/**
 * @author Dariusz Stefanski
 */
class CarToStoreExamples {

    static final CarToStore OPEL_CORSA_TO_STORE = new CarToStore(
            make: 'Opel', model: 'Corsa', year: 2010, engineDisplacement: 1300,
            attributes: ['origin': 'Poland', 'mileage': '90120']
    );

    static final CarToStore CAR_WITHOUT_MODEL_TO_STORE = new CarToStore(
            make: 'Opel', year: 2010, engineDisplacement: 1300,
            attributes: ['origin': 'Poland', 'mileage': '90120']
    );
}

package com.stefanski.cars.store;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Dariusz Stefanski
 */
interface CarRepository extends CrudRepository<Car, Long> {
}

package com.stefanski.cars.store.events;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import com.stefanski.cars.store.Car;

/**
 * @author Dariusz Stefanski
 */
@Getter
@ToString
public class UpdatedCarEvent extends ApplicationEvent {

    private Car car;

    public UpdatedCarEvent(Car car) {
        super(car);
        this.car = car;
    }
}

package com.stefanski.cars.store;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Dariusz Stefanski
 */
@Getter
public class NewCarEvent extends ApplicationEvent {

    private Car car;

    public NewCarEvent(Car car) {
        super(car);
        this.car = car;
    }
}

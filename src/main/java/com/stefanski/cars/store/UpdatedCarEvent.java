package com.stefanski.cars.store;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Dariusz Stefanski
 */
@Getter
public class UpdatedCarEvent extends ApplicationEvent {

    private Car car;

    public UpdatedCarEvent(Car car) {
        super(car);
        this.car = car;
    }
}

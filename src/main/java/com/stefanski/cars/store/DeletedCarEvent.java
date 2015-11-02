package com.stefanski.cars.store;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Dariusz Stefanski
 */
@Getter
public class DeletedCarEvent extends ApplicationEvent {

    private Long carId;

    public DeletedCarEvent(Long carId) {
        super(carId);
        this.carId = carId;
    }
}

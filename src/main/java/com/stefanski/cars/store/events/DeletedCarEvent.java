package com.stefanski.cars.store.events;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author Dariusz Stefanski
 */
@Getter
@ToString
public class DeletedCarEvent extends ApplicationEvent {

    private Long carId;

    public DeletedCarEvent(Long carId) {
        super(carId);
        this.carId = carId;
    }
}

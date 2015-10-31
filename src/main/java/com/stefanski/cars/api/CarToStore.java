package com.stefanski.cars.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import com.stefanski.cars.store.Attribute;
import com.stefanski.cars.store.Car;


/**
 * @author Dariusz Stefanski
 */
@Data
@ApiModel
class CarToStore {

    @NotEmpty
    @ApiModelProperty(required = true)
    private String make;

    @NotEmpty
    @ApiModelProperty(required = true)
    private String model;

    @NotNull
    @ApiModelProperty(required = true)
    private Integer year;

    @NotNull
    @ApiModelProperty(required = true)
    private Integer engineDisplacement;

    private Map<String, String> attributes = new HashMap<>();

    public CarToStore() {
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    Car toCar() {
        return new Car(make, model, year, engineDisplacement, createCarAttributes());
    }

    private Set<Attribute> createCarAttributes() {
        Set<Attribute> carAttributes = new HashSet<>();
        attributes.forEach((name, value) -> {
            Attribute attribute = new Attribute(name, value.toString());
            carAttributes.add(attribute);
        });
        return carAttributes;
    }
}


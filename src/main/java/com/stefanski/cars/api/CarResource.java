package com.stefanski.cars.api;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import com.stefanski.cars.store.Attribute;
import com.stefanski.cars.store.Car;

/**
 * @author Dariusz Stefanski
 */
@ApiModel
@Data
@NoArgsConstructor
public class CarResource {

    @ApiModelProperty(required = false)
    private Long id;

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

    @ApiModelProperty(required = false)
    private Map<String, String> attributes;

    public CarResource(Long id, String make, String model, Integer year, Integer engineDisplacement,
                       Map<String, String> attributes) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engineDisplacement = engineDisplacement;
        this.attributes = attributes;
    }

    public static CarResource fromCar(Car car) {
        return new CarResource(car.getId(), car.getMake(), car.getModel(), car.getYear(),
                car.getEngineDisplacement(), car.getAttributesMap());
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    public Car toCar() {
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

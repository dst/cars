package com.stefanski.cars.store;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;


/**
 * @author Dariusz Stefanski
 */
@Data
@ApiModel
class CarDto {

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

    public CarDto() {
    }

    public String getAttribute(String name) {
        return attributes.get(name);
    }

    public static CarDto fromCar(Car car) {
        CarDto carDto = new CarDto();
        BeanUtils.copyProperties(car, carDto);
        carDto.setAttributes(car.getAttributesMap());
        return carDto;
    }

}


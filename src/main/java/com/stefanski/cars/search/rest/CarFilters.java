package com.stefanski.cars.search.rest;

import java.util.Map;
import javax.validation.constraints.NotNull;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Dariusz Stefanski
 */
@ApiModel
@Data
public class CarFilters {

    @NotNull
    @ApiModelProperty(required = true)
    private Map<String, String> attributes;
}

package com.stefanski.cars.api;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Dariusz Stefanski
 */
@ApiModel
@Data
public class CreationResp {

    @ApiModelProperty(value = "ID of created object", required = true)
    private final Long id;
}

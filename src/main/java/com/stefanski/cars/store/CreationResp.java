package com.stefanski.cars.store;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author Dariusz Stefanski
 */
@ApiModel
@Data
class CreationResp {

    @ApiModelProperty(value = "ID of created object", required = true)
    private final Long id;
}

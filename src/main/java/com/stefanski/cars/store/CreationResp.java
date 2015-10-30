package com.stefanski.cars.store;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Dariusz Stefanski
 */
@Data
@ApiModel
class CreationResp {

    @ApiModelProperty(value = "ID of created object", required = true)
    private Long id;

    public CreationResp(Long id) {
        this.id = id;
    }
}

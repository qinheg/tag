package com.louddt.tag.entity.vo.model;

import io.swagger.annotations.ApiModelProperty;

public class ModelDeteleBO {

    @ApiModelProperty(value = "模型id，多个id以英文逗号隔开",required = true)
    private String modelIds;

    public String getModelIds() {
        return modelIds;
    }

    public void setModelIds(String modelIds) {
        this.modelIds = modelIds;
    }
}

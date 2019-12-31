package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskEntryProperty {

    @ApiModelProperty("属性名")
    private String name;
    @ApiModelProperty("属性值")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

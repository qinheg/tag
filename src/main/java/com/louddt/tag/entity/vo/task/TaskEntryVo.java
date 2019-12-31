package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskEntryVo {

    @ApiModelProperty("实体或关系名")
    private String name;
    @ApiModelProperty("类型  1-实体  2-关系")
    private Integer type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

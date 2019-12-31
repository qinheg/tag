package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskRelationRuleVo {

    @ApiModelProperty("关系名称")
    private String name;
    @ApiModelProperty("起始实体")
    private String from;
    @ApiModelProperty("目标实体")
    private String to;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

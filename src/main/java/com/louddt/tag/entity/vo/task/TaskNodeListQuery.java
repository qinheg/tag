package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskNodeListQuery {

    @ApiModelProperty(value = "标注任务id",required = true)
    private String taskId;
    @ApiModelProperty(value = "类型1-实体或分类  2-关系",required = true)
    private Integer type;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

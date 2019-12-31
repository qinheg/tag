package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskDatasetMappingQuery {

    @ApiModelProperty(value = "标注任务id",required = true)
    private String taskId;
    @ApiModelProperty(value = "实体或关系名称,如果不传实体名，则查询默认配置",required = false)
    private String node;
    @ApiModelProperty(value = "类型  1-实体  2-关系",required = false)
    private Integer type;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

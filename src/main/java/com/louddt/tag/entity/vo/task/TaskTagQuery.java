package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskTagQuery {

    @ApiModelProperty(value = "标注任务id",required = true)
    private String taskId;
    @ApiModelProperty(value = "文本序号，默认为第一个",required = false)
    private Integer fileSort = 1;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getFileSort() {
        return fileSort;
    }

    public void setFileSort(Integer fileSort) {
        this.fileSort = fileSort;
    }
}

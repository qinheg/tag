package com.louddt.tag.entity.vo.task;

import io.swagger.annotations.ApiModelProperty;

public class TaskTagSaveBO {

    @ApiModelProperty(value = "标注任务id" ,required = true)
    private String taskId;
    @ApiModelProperty(value = "标注文本序号" ,required = true)
    private Integer fileSort;
    @ApiModelProperty(value = "标注结果内容" ,required = true)
    private String dataJson;

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

    public String getDataJson() {
        return dataJson;
    }

    public void setDataJson(String dataJson) {
        this.dataJson = dataJson;
    }
}
